package br.com.checkdiff.gateway;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import reactor.core.publisher.Mono;

public interface FindComparisonResultGateway {
    Mono<ComparisonResultDomain> findById(Long id) throws FindComparisonException;
}
