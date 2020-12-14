package br.com.zup.renatomelo.proposta.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class CartaoBloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String enderecoIp;
    @NotBlank
    private String userAgent;
    @OneToOne
    private Cartao cartao;

    /**
     * @param enderecoIp = endereço IP de quem fez a request
     * @param userAgent = User Agent de quem fez a request
     * @param cartao = cartão associado
     */
    public CartaoBloqueio(String enderecoIp, String userAgent, Cartao cartao) {
        this.enderecoIp = enderecoIp;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    @Deprecated
    public CartaoBloqueio() {
    }
}
