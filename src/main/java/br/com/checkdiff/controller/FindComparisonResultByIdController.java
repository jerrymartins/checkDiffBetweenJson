package br.com.checkdiff.controller;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import br.com.checkdiff.usecase.FindComparisonResultByIdUseCase;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("find-comparison")
public class FindComparisonResultByIdController {
    private final FindComparisonResultByIdUseCase findComparisonResultByIdUseCase;

    public FindComparisonResultByIdController(FindComparisonResultByIdUseCase findComparisonResultByIdUseCase) {
        this.findComparisonResultByIdUseCase = findComparisonResultByIdUseCase;
    }

    @GetMapping(path = "{id}")
    public Mono<ComparisonResultDomain> findById(@PathVariable @NotNull Long id) throws FindComparisonException {
        return findComparisonResultByIdUseCase.execute(id);
    }
}
