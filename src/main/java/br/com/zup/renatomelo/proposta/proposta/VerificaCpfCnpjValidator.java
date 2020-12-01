package br.com.zup.renatomelo.proposta.proposta;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VerificaCpfCnpjValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NovaPropostaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        if(errors.hasErrors()){
            return;
        }

        NovaPropostaRequest novaPropostaRequest = (NovaPropostaRequest) o;

        if(!novaPropostaRequest.documentValid()) {
            errors.rejectValue("documento", null, "documento precisa ser cpf ou cnpj");
        }
    }
}
