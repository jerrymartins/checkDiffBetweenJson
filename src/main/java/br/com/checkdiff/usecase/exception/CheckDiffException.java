package br.com.checkdiff.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CheckDiffException extends Exception {
    public CheckDiffException(String message) {
        super(message);
    }
}