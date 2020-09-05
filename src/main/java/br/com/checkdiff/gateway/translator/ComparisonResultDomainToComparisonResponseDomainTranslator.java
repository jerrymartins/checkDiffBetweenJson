package br.com.checkdiff.gateway.translator;

import br.com.checkdiff.domain.ComparisonResponseDomain;
import br.com.checkdiff.domain.ComparisonResultDomain;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ComparisonResultDomainToComparisonResponseDomainTranslator {

    public ComparisonResponseDomain translate(ComparisonResultDomain comparisonResultDomain) {
        return ComparisonResponseDomain.builder()
                .differentFields(comparisonResultDomain.getDifferentFields())
                .onlyLeftFields(comparisonResultDomain.getOnlyLeftFields())
                .onlyRightFields(comparisonResultDomain.getOnlyRightFields())
                .build();
    }
}
