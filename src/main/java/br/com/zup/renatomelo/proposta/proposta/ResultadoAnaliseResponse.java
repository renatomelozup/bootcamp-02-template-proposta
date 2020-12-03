package br.com.zup.renatomelo.proposta.proposta;

public class ResultadoAnaliseResponse {
    private String documento;
    private String nome;
    private String resultadoSolicitacao;
    private String idProposta;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Deprecated
    public ResultadoAnaliseResponse() {
    }

    public ResultadoAnaliseResponse(String documento,
                                    String nome,
                                    String resultadoSolicitacao,
                                    String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

}
