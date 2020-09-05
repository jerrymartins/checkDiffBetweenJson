package br.com.checkdiff.gateway.translator;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.database.entity.ComparisonResultEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ComparisonResultDomainToComparisonResultEntityTranslator {

    public ComparisonResultEntity translate(ComparisonResultDomain comparisonResultDomain) {
        return ComparisonResultEntity.builder()
                .leftData(comparisonResultDomain.getLeftData())
                .rightData(comparisonResultDomain.getRightData())
                .differentFields(comparisonResultDomain.getDifferentFields())
                .onlyLeftFields(comparisonResultDomain.getOnlyLeftFields())
                .onlyRightFields(comparisonResultDomain.getOnlyRightFields())
                .build();
    }
}
