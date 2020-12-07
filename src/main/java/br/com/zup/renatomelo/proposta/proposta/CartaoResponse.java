package br.com.zup.renatomelo.proposta.proposta;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartaoResponse {
    private String id;

    @Deprecated
    public CartaoResponse() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CartaoResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
