package br.com.zup.renatomelo.proposta.proposta.controller;

import br.com.zup.renatomelo.proposta.advice.ApiErrorException;
import br.com.zup.renatomelo.proposta.proposta.model.Biometria;
import br.com.zup.renatomelo.proposta.proposta.model.Cartao;
import br.com.zup.renatomelo.proposta.proposta.model.CartaoBloqueio;
import br.com.zup.renatomelo.proposta.proposta.model.CartaoStatus;
import br.com.zup.renatomelo.proposta.proposta.repository.BiometriaRepository;
import br.com.zup.renatomelo.proposta.proposta.repository.CartaoBloqueioRepository;
import br.com.zup.renatomelo.proposta.proposta.repository.CartaoRepository;
import br.com.zup.renatomelo.proposta.proposta.request.NovaBiometriaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoBloqueioRepository cartaoBloqueioRepository;
    @Autowired
    private BiometriaRepository biometriaRepository;

    @PostMapping("/{id}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable UUID id, HttpServletRequest request) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);

        if(cartaoOptional.isEmpty()) {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Não encontrado");
        }

        Cartao cartaoNoBanco = cartaoOptional.get();
        if(cartaoNoBanco.getStatus() == CartaoStatus.BLOQUEADO) {
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Já bloqueado");
        }

        String enderecoIp = request.getHeader("X-FORWARDED-FOR");
        if(enderecoIp == null) {
            enderecoIp = request.getRemoteAddr();
        }

        String userAgent = request.getHeader("User-Agent");

        CartaoBloqueio cartaoBloqueio = new CartaoBloqueio(enderecoIp, userAgent, cartaoNoBanco);

        cartaoBloqueioRepository.save(cartaoBloqueio);

        cartaoNoBanco.setCartaoBloqueio(cartaoBloqueio);
        cartaoNoBanco.setStatus(CartaoStatus.BLOQUEADO);
        cartaoRepository.save(cartaoNoBanco);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/{id}/biometria")
    @Transactional
    public ResponseEntity<?> cadastrarBiometria (@PathVariable UUID id,
                                        @RequestBody @Valid NovaBiometriaRequest biometria,
                                        UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if(cartao.isEmpty()) {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "registro não encontrado");
        }


        Biometria novaBiometria = biometria.toModel();
        novaBiometria.setCartao(cartao.get());
        biometriaRepository.save(novaBiometria);

        cartao.get().getBiometriaList().add(novaBiometria);

        URI uri = uriComponentsBuilder.path("/api/v1/cartoes/{id}/biometria/{biometriaId}").buildAndExpand(id.toString(), novaBiometria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
