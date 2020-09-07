package br.com.checkdiff.gateway.database;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.SaveComparisonResultGateway;
import br.com.checkdiff.gateway.database.entity.ComparisonResultEntity;
import br.com.checkdiff.gateway.database.repository.SaveComparisonRepository;
import br.com.checkdiff.gateway.exception.SaveComparisonException;
import br.com.checkdiff.gateway.translator.ComparisonResultDomainToComparisonResultEntityTranslator;
import br.com.checkdiff.gateway.translator.ComparisonResultEntityToComparisonResultDomainTranslator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveComparisonResultImplByIdGateway implements SaveComparisonResultGateway {
    private final SaveComparisonRepository comparisonRepository;

    public SaveComparisonResultImplByIdGateway(SaveComparisonRepository comparisonRepository) {
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

}
