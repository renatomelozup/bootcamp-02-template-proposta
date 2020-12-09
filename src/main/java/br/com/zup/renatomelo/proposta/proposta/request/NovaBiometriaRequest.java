package br.com.zup.renatomelo.proposta.proposta.request;

import br.com.zup.renatomelo.proposta.proposta.model.Biometria;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class NovaBiometriaRequest {

    @NotBlank
    private String biometria;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaBiometriaRequest(String biometria) {
        this.biometria = biometria;
    }

   public Biometria toModel() {
        byte[] biometria = Base64.getDecoder().decode(this.biometria.getBytes());
        Biometria novaBiometria = new Biometria(biometria);
        return novaBiometria;
    }
}
