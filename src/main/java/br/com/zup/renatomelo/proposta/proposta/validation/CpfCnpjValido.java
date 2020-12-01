package br.com.zup.renatomelo.proposta.proposta.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {CpfCnpjValidoValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpjValido {

    String message() default "{br.com.zup.renatomelo.beanvalidation.cpfcnpjinvalido}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
