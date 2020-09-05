package br.com.checkdiff.usecase;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.FindComparisonResultGateway;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindComparisonResultUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindComparisonResultUseCase.class);

    private final FindComparisonResultGateway findComparisonResultGateway;

    public FindComparisonResultUseCase(FindComparisonResultGateway findComparisonResultGateway) {
        this.findComparisonResultGateway = findComparisonResultGateway;
    }

    public Mono<ComparisonResultDomain> findById(Long id) throws FindComparisonException {
        try {
            LOGGER.info("Searching Comparison result with id {}", id);
            return findComparisonResultGateway.findById(id);
        } catch (Exception exception) {
            throw new FindComparisonException("erro a descrever");
        }
    }

}
