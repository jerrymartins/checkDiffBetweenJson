package br.com.checkdiff.usecase;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.FindComparisonResultByIdGateway;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindComparisonResultByIdUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindComparisonResultByIdUseCase.class);

    private final FindComparisonResultByIdGateway findComparisonResultByIdGateway;

    public FindComparisonResultByIdUseCase(FindComparisonResultByIdGateway findComparisonResultByIdGateway) {
        this.findComparisonResultByIdGateway = findComparisonResultByIdGateway;
    }

    /**
     * search of a comparison result
     * @param id
     * @return
     * @throws FindComparisonException
     */
    public Mono<ComparisonResultDomain> execute(Long id) throws FindComparisonException {
        LOGGER.info("Searching Comparison result with id {}", id);
        return findComparisonResultByIdGateway.findById(id);
    }

}
