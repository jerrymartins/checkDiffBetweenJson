package br.com.checkdiff.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataEqualsException extends Exception {
    public DataEqualsException(String message) {
        super(message);
    }
}