package br.com.zup.renatomelo.proposta.advice;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final String reason;

    public ApiErrorException(HttpStatus httpStatus, String reason) {
        super(reason);
        this.reason = reason;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReason() {
        return reason;
    }
}
