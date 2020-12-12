package br.com.zup.renatomelo.proposta.proposta.controller;

import br.com.zup.renatomelo.proposta.advice.ApiErrorException;
import br.com.zup.renatomelo.proposta.proposta.model.Cartao;
import br.com.zup.renatomelo.proposta.proposta.model.CartaoBloqueio;
import br.com.zup.renatomelo.proposta.proposta.repository.CartaoBloqueioRepository;
import br.com.zup.renatomelo.proposta.proposta.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoBloqueioRepository cartaoBloqueioRepository;

    @PostMapping("/{id}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable UUID id, HttpServletRequest request) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);

        if(cartaoOptional.isEmpty()) {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Não encontrado");
        }

        Cartao cartaoNoBanco = cartaoOptional.get();
        if(cartaoNoBanco.getCartaoBloqueio() != null) {
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
        cartaoRepository.save(cartaoNoBanco);

        return ResponseEntity.ok().build();
    }
}
