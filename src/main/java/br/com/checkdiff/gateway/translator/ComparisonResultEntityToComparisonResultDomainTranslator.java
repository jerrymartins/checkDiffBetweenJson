package br.com.checkdiff.gateway.translator;

import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.database.entity.ComparisonResultEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ComparisonResultEntityToComparisonResultDomainTranslator {

    public ComparisonResultDomain translate(ComparisonResultEntity comparisonResultEntity) {
        return ComparisonResultDomain.builder()
                .id(comparisonResultEntity.getId())
                .leftData(comparisonResultEntity.getLeftData())
                .rightData(comparisonResultEntity.getRightData())
                .differentFields(comparisonResultEntity.getDifferentFields())
                .onlyLeftFields(comparisonResultEntity.getOnlyLeftFields())
                .onlyRightFields(comparisonResultEntity.getOnlyRightFields())
                .build();
    }
}
