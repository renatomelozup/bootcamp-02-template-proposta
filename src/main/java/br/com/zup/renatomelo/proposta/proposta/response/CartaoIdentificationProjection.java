package br.com.zup.renatomelo.proposta.proposta.response;

import br.com.zup.renatomelo.proposta.proposta.model.CartaoStatus;

import java.util.UUID;

public interface CartaoIdentificationProjection {
    UUID getId();
    CartaoStatus getStatus();
}
