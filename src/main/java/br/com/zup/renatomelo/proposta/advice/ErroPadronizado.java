package br.com.zup.renatomelo.proposta.advice;

import java.util.Collection;

public class ErroPadronizado {

    private Collection<String> mensagens;

    public Collection<String> getMensagens() {
        return mensagens;
    }

    public ErroPadronizado(Collection<String> mensagens) {
        this.mensagens = mensagens;
    }
}
