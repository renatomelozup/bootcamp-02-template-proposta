package br.com.zup.renatomelo.proposta.proposta.response;

import br.com.zup.renatomelo.proposta.proposta.model.Cartao;
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

    public Cartao toModel() {
        return new Cartao(this.id);
    }
}
