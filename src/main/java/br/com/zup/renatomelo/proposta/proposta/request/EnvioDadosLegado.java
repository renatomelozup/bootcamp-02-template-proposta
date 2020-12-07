package br.com.zup.renatomelo.proposta.proposta.request;

public class EnvioDadosLegado {

    private String documento;
    private String nome;
    private String idProposta;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Deprecated
    public EnvioDadosLegado() {
    }

    public EnvioDadosLegado(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }
}
