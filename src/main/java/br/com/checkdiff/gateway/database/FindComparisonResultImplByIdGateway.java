package br.com.checkdiff.gateway.database;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.FindComparisonResultByIdGateway;
import br.com.checkdiff.gateway.database.repository.SaveComparisonRepository;
import br.com.checkdiff.gateway.translator.ComparisonResultEntityToComparisonResultDomainTranslator;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import br.com.checkdiff.gateway.exception.NotFoundComparisonException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindComparisonResultImplByIdGateway implements FindComparisonResultByIdGateway {
    private final SaveComparisonRepository comparisonRepository;

    public FindComparisonResultImplByIdGateway(SaveComparisonRepository comparisonRepository) {
        this.comparisonRepository = comparisonRepository;
    }

    /**
     * searches for a comparison result in the database
     * @param id
     * @return
     * @throws FindComparisonException
     */
    @Override
    public Mono<ComparisonResultDomain> findById(Long id) throws FindComparisonException {
        try {
            return comparisonRepository.findById(id).map(ComparisonResultEntityToComparisonResultDomainTranslator::translate)
                    .switchIfEmpty(Mono.error(new NotFoundComparisonException("comparison not found " + id)));
        } catch (Exception exception) {
            throw new FindComparisonException(exception.getMessage());
        }

    }
}
