package br.com.zup.renatomelo.proposta.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizado> handle(MethodArgumentNotValidException methodArgumentNotValidException) {

        Collection<String> mensagens = new ArrayList<>();

        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        fieldErrorList.forEach(fieldError -> {
            String message = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
            mensagens.add(message);
        });

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErroPadronizado> handleApiErrorException(ApiErrorException apiErrorException) {

        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(apiErrorException.getReason());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(apiErrorException.getHttpStatus()).body(erroPadronizado);
    }
}
