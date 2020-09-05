package br.com.checkdiff.gateway;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.exception.SaveComparisonException;
import reactor.core.publisher.Mono;

public interface SaveComparisonResultGateway {
    Mono<ComparisonResultDomain> save(ComparisonResultDomain comparisonResultDomain) throws SaveComparisonException;
}
