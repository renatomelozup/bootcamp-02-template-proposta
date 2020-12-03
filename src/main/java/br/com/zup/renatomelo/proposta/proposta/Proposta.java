package br.com.zup.renatomelo.proposta.proposta;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Column(unique = true)
    private String documento;

    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @Deprecated
    public Proposta() {
    }

    /**
     * @param documento = CPF ou CNPJ não pode ser nulo ou vazio
     * @param email = email valido e não não nulo
     * @param nome = não pode ser em branco ou nulo
     * @param endereco = não pode ser em branco ou nulo
     * @param salario = não pode ser nulo e deve ser maior que 0
     */
    public Proposta(@NotBlank String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @Positive @NotNull BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public UUID getId() {
        return this.id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void setEstado(StatusProposta status) {
        this.status = status;
    }
}
