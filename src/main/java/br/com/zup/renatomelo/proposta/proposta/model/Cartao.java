package br.com.zup.renatomelo.proposta.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotBlank
    private String numeroCartao;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometriaList = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    /**
     * @param id = id passado pela api legado, equivale ao número do cartão
     */
    public Cartao(String id) {
        this.numeroCartao = id;
    }

    public List<Biometria> getBiometriaList() {
        return biometriaList;
    }
}
