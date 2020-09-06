package br.com.checkdiff.controller;

import br.com.checkdiff.domain.ComparisonRequestDomain;
import br.com.checkdiff.domain.ComparisonResponseDomain;
import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import br.com.checkdiff.gateway.exception.SaveComparisonException;
import br.com.checkdiff.usecase.CheckDiffUseCase;
import br.com.checkdiff.usecase.FindComparisonResultUseCase;
import br.com.checkdiff.usecase.exception.DataEqualsException;
import br.com.checkdiff.usecase.exception.InvalidJsonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("check-diff")
public class CheckDiffController {

    private final CheckDiffUseCase checkDiffUseCase;
    private final FindComparisonResultUseCase findComparisonResultUseCase;

    public CheckDiffController(CheckDiffUseCase checkDiffUseCase, FindComparisonResultUseCase findComparisonResultUseCase) {
        this.checkDiffUseCase = checkDiffUseCase;
        this.findComparisonResultUseCase = findComparisonResultUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ComparisonResponseDomain> check(@RequestBody @Valid ComparisonRequestDomain comparisonRequestDomain) throws DataEqualsException, SaveComparisonException, InvalidJsonException {
        return checkDiffUseCase.execute(comparisonRequestDomain);
    }

    @GetMapping(path = "{id}")
    public Mono<ComparisonResultDomain> findById(@PathVariable @NotNull Long id) throws FindComparisonException {
        return findComparisonResultUseCase.execute(id);
    }
}
