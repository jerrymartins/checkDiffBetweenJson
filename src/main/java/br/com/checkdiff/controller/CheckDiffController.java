package br.com.checkdiff.controller;

import br.com.checkdiff.domain.ComparisonRequest;
import br.com.checkdiff.domain.ComparisonResponseDomain;
import br.com.checkdiff.gateway.exception.SaveComparisonException;
import br.com.checkdiff.usecase.CheckDiffUseCase;
import br.com.checkdiff.usecase.exception.DataEqualsException;
import br.com.checkdiff.usecase.exception.InvalidJsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("check-diff")
public class CheckDiffController {

    private final CheckDiffUseCase checkDiffUseCase;

    public CheckDiffController(CheckDiffUseCase checkDiffUseCase) {
        this.checkDiffUseCase = checkDiffUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ComparisonResponseDomain> check(@RequestBody @Valid ComparisonRequest comparisonRequest) throws DataEqualsException, SaveComparisonException, InvalidJsonException {
        return checkDiffUseCase.execute(comparisonRequest);
    }

}
