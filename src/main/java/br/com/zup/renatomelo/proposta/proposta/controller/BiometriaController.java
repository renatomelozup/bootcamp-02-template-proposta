package br.com.zup.renatomelo.proposta.proposta.controller;

import br.com.zup.renatomelo.proposta.advice.ApiErrorException;
import br.com.zup.renatomelo.proposta.proposta.model.Biometria;
import br.com.zup.renatomelo.proposta.proposta.model.Cartao;
import br.com.zup.renatomelo.proposta.proposta.repository.BiometriaRepository;
import br.com.zup.renatomelo.proposta.proposta.repository.CartaoRepository;
import br.com.zup.renatomelo.proposta.proposta.request.NovaBiometriaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/biometrias")
public class BiometriaController {

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar (@RequestParam UUID idCartao,
                                        @RequestBody @Valid NovaBiometriaRequest biometria,
                                        UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartao = cartaoRepository.findById(idCartao);
        if(cartao.isEmpty()) {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "registro não encontrado");
        }


        Biometria novaBiometria = biometria.toModel();
        novaBiometria.setCartao(cartao.get());
        biometriaRepository.save(novaBiometria);

        cartao.get().getBiometriaList().add(novaBiometria);

        URI uri = uriComponentsBuilder.path("/api/v1/biometrias/{id}").buildAndExpand(novaBiometria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
