package br.com.zup.renatomelo.proposta.proposta.response;

import br.com.zup.renatomelo.proposta.proposta.model.StatusProposta;

import java.math.BigDecimal;

public interface DadosPropostaProjection {
    String getDocumento();
    String getNome();
    String getEmail();
    String getEndereco();
    BigDecimal getSalario();
    StatusProposta getStatus();
}
