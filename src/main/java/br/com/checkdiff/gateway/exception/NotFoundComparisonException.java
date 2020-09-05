package br.com.checkdiff.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundComparisonException extends Exception {

    public NotFoundComparisonException(String message) {
        super(message);
    }
}