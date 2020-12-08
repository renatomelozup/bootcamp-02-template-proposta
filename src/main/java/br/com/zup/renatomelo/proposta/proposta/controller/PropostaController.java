package br.com.zup.renatomelo.proposta.proposta.controller;

import br.com.zup.renatomelo.proposta.advice.ApiErrorException;
import br.com.zup.renatomelo.proposta.proposta.model.Proposta;
import br.com.zup.renatomelo.proposta.proposta.model.StatusProposta;
import br.com.zup.renatomelo.proposta.proposta.repository.PropostaRepository;
import br.com.zup.renatomelo.proposta.proposta.request.EnvioDadosLegado;
import br.com.zup.renatomelo.proposta.proposta.request.NovaPropostaRequest;
import br.com.zup.renatomelo.proposta.proposta.response.DadosPropostaProjection;
import br.com.zup.renatomelo.proposta.proposta.response.ResultadoAnaliseResponse;
import br.com.zup.renatomelo.proposta.proposta.service.AnaliseCliente;
import br.com.zup.renatomelo.proposta.proposta.service.CartaoClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dados")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseCliente analiseCliente;

    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping
    @Transactional
    public ResponseEntity<?> receberDados(@RequestBody @Valid NovaPropostaRequest novaProposta,
                                          UriComponentsBuilder uriComponentsBuilder) {

        if(propostaRepository.findByDocumento(novaProposta.getDocumento()).isPresent()) {
            String mensagem = String.format("O documento %s já possui cadastro", novaProposta.getDocumento());
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, mensagem);
        }

        Proposta proposta = novaProposta.toModel();

        propostaRepository.save(proposta);

        EnvioDadosLegado envioDadosLegado = new EnvioDadosLegado(proposta.getDocumento(),
                proposta.getNome(),
                proposta.getId().toString());

        ResultadoAnaliseResponse resultadoAnaliseResponse;

        try {
            resultadoAnaliseResponse = analiseCliente.analise(envioDadosLegado);
            if (resultadoAnaliseResponse.getResultadoSolicitacao().equalsIgnoreCase("SEM_RESTRICAO")){
                proposta.setEstado(StatusProposta.ELEGIVEL);
            }
        } catch (FeignException.UnprocessableEntity unprocessableEntity) {
            try {
                resultadoAnaliseResponse = new ObjectMapper().readValue(unprocessableEntity.contentUTF8(),
                        ResultadoAnaliseResponse.class);
                if(resultadoAnaliseResponse.getResultadoSolicitacao().equalsIgnoreCase("COM_RESTRICAO")){
                    proposta.setEstado(StatusProposta.NAO_ELEGIVEL);
                }
            } catch (JsonProcessingException jsonProcessingException) {
                throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Serviço indisponivel");
            }
        } catch (FeignException feignException) {
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Serviço indisponivel");
        }

        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/api/v1/dados/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> checaProposta(@PathVariable UUID id) {
        Optional<DadosPropostaProjection> dados = propostaRepository.getById(id);
        if(dados.isPresent()) {
            return ResponseEntity.ok(dados);
        }
        throw new ApiErrorException(HttpStatus.NOT_FOUND, "Registro não encontrado");
    }
}
