package br.com.zup.renatomelo.proposta.proposta.service;

import br.com.zup.renatomelo.proposta.proposta.repository.PropostaRepository;
import br.com.zup.renatomelo.proposta.proposta.model.Proposta;
import br.com.zup.renatomelo.proposta.proposta.model.StatusProposta;
import br.com.zup.renatomelo.proposta.proposta.response.CartaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssociaCartaoProposta {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private CartaoClient cartaoClient;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    @Transactional
    private void associaCartao() {
        List<Proposta> listAll = repository.findAllByStatusAndCartaoId(StatusProposta.ELEGIVEL, null);
        List<CartaoResponse> cartaoResponseArrayList = new ArrayList<>();
        listAll.forEach(proposta -> {
            CartaoResponse cartaoResponse = cartaoClient.checarCartao(proposta.getId().toString());
            System.out.println(cartaoResponse.getId());
            proposta.setCartaoId(cartaoResponse.getId());
            repository.save(proposta);
        });
    }
}
