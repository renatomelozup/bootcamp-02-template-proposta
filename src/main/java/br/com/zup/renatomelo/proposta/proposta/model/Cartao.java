package br.com.zup.renatomelo.proposta.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.com.zup.renatomelo.proposta.proposta.model.CartaoStatus.ATIVO;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String numeroCartao;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometriaList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CartaoStatus status = ATIVO;

    @OneToOne
    private CartaoBloqueio cartaoBloqueio;

    @Deprecated
    public Cartao() {
    }

    /**
     * @param id = id passado pela api legado, equivale ao número do cartão
     */
    public Cartao(String id) {
        this.numeroCartao = id;
    }

    public UUID getId() {
        return id;
    }

    public CartaoStatus getStatus() {
        return status;
    }

    public void setStatus(CartaoStatus status) {
        this.status = status;
    }

    public List<Biometria> getBiometriaList() {
        return biometriaList;
    }

    public void setCartaoBloqueio(CartaoBloqueio cartaoBloqueio) {
        this.cartaoBloqueio = cartaoBloqueio;
    }

    public CartaoBloqueio getCartaoBloqueio() {
        return cartaoBloqueio;
    }
}
