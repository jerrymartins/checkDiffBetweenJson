package br.com.checkdiff.gateway.database;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.FindComparisonResultGateway;
import br.com.checkdiff.gateway.SaveComparisonResultGateway;
import br.com.checkdiff.gateway.database.entity.ComparisonResultEntity;
import br.com.checkdiff.gateway.database.repository.SaveComparisonRepository;
import br.com.checkdiff.gateway.translator.ComparisonResultDomainToComparisonResultEntityTranslator;
import br.com.checkdiff.gateway.translator.ComparisonResultEntityToComparisonResultDomainTranslator;
import br.com.checkdiff.gateway.exception.FindComparisonException;
import br.com.checkdiff.gateway.exception.NotFoundComparisonException;
import br.com.checkdiff.gateway.exception.SaveComparisonException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ComparisonDataImplGateway implements SaveComparisonResultGateway, FindComparisonResultGateway {
    private final SaveComparisonRepository comparisonRepository;

    public ComparisonDataImplGateway(SaveComparisonRepository comparisonRepository) {
        this.comparisonRepository = comparisonRepository;
    }

    /**
     * saves result of comparison in database
     * @param comparisonResultDomain
     * @return
     * @throws SaveComparisonException
     */
    @Override
    public Mono<ComparisonResultDomain> save(ComparisonResultDomain comparisonResultDomain) throws SaveComparisonException {
        try {
            ComparisonResultEntity entity = ComparisonResultDomainToComparisonResultEntityTranslator.translate(comparisonResultDomain);
            return comparisonRepository.save(entity).map(ComparisonResultEntityToComparisonResultDomainTranslator::translate);
        } catch (Exception exception) {
            throw new SaveComparisonException(exception.getMessage());
        }

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
                    .switchIfEmpty(Mono.error(new NotFoundComparisonException("comparison not found")));
        } catch (Exception exception) {
            throw new FindComparisonException(exception.getMessage());
        }

    }
}
