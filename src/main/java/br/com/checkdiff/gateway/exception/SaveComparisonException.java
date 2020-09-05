package br.com.checkdiff.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SaveComparisonException extends Exception {
    public SaveComparisonException(String message) {
        super(message);
    }
}