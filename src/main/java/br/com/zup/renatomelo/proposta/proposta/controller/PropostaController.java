package br.com.zup.renatomelo.proposta.proposta.controller;

import br.com.zup.renatomelo.proposta.advice.ApiErrorException;
import br.com.zup.renatomelo.proposta.proposta.*;
import br.com.zup.renatomelo.proposta.proposta.model.Proposta;
import br.com.zup.renatomelo.proposta.proposta.model.StatusProposta;
import br.com.zup.renatomelo.proposta.proposta.request.AnaliseRequest;
import br.com.zup.renatomelo.proposta.proposta.request.NovaPropostaRequest;
import br.com.zup.renatomelo.proposta.proposta.response.ResultadoAnaliseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/dados")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseCliente analiseCliente;

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

        AnaliseRequest analiseRequest = new AnaliseRequest(proposta.getDocumento(),
                proposta.getNome(),
                proposta.getId().toString());

        ResultadoAnaliseResponse resultadoAnaliseResponse;

        try {
            resultadoAnaliseResponse = analiseCliente.analise(analiseRequest);
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
        }

        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/api/dados/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
