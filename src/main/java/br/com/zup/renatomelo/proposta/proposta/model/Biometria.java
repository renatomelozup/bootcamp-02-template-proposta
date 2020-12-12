package br.com.zup.renatomelo.proposta.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Lob
    @NotNull
    private byte[] biometria;

    @FutureOrPresent
    private LocalDate dataCriacao = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "cartaoId")
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    /**
     * @param biometria = byte[] convertido da string em Base64 referente ao fingerprint da biometria
     */
    public Biometria(byte[] biometria) {
        this.biometria = biometria;
    }

    public UUID getId() {
        return id;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

}
