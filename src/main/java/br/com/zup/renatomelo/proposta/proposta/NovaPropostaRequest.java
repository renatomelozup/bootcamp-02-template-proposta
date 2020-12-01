package br.com.zup.renatomelo.proposta.proposta;

import br.com.zup.renatomelo.proposta.proposta.validation.CpfCnpjValido;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @CpfCnpjValido
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Positive
    @NotNull
    private BigDecimal salario;

    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento = CPF ou CNPJ não pode ser nulo ou vazio
     * @param email = email valido e não não nulo
     * @param nome = não pode ser em branco ou nulo
     * @param endereco = não pode ser em branco ou nulo
     * @param salario = não pode ser nulo e deve ser maior que 0
     */
    public NovaPropostaRequest(@NotBlank String documento,
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

    public Proposta toModel() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
    }
}
