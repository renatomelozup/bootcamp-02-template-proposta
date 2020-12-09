package br.com.zup.renatomelo.proposta.proposta.service;

import br.com.zup.renatomelo.proposta.advice.ApiErrorException;
import br.com.zup.renatomelo.proposta.proposta.model.Cartao;
import br.com.zup.renatomelo.proposta.proposta.repository.CartaoRepository;
import br.com.zup.renatomelo.proposta.proposta.repository.PropostaRepository;
import br.com.zup.renatomelo.proposta.proposta.model.Proposta;
import br.com.zup.renatomelo.proposta.proposta.model.StatusProposta;
import br.com.zup.renatomelo.proposta.proposta.response.CartaoResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssociaCartaoProposta {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoClient cartaoClient;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    @Transactional
    private void associaCartao() {
        List<Proposta> listAll = propostaRepository.findAllByStatusAndCartaoId(StatusProposta.ELEGIVEL, null);
        List<CartaoResponse> cartaoResponseArrayList = new ArrayList<>();
        listAll.forEach(proposta -> {

            Cartao cartao = solicitaCartao(proposta.getId().toString());

            proposta.setCartao(cartao);

            propostaRepository.save(proposta);

        });
    }

    @Transactional
    private Cartao solicitaCartao(String id) {
        try {
            CartaoResponse cartaoResponse = cartaoClient.checarCartao(id);
            Cartao cartao = cartaoResponse.toModel();
            cartaoRepository.save(cartao);
            return cartao;
        }catch (FeignException.FeignServerException feignServerException) {
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Serviço indisponivel");
        }
    }
}
