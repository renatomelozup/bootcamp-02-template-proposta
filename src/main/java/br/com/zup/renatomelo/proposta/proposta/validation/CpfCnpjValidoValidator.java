package br.com.zup.renatomelo.proposta.proposta.validation;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidoValidator implements ConstraintValidator<CpfCnpjValido, String> {

    @Override
    public void initialize(CpfCnpjValido constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        Assert.hasLength(s, "não pode validar vazio");

        CPFValidator cpfValidator = new CPFValidator();
        CNPJValidator cnpjValidator = new CNPJValidator();

        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(s, null) || cnpjValidator.isValid(s, null);
    }
}
