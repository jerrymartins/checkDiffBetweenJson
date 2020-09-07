package br.com.checkdiff.usecase;

import br.com.checkdiff.domain.ComparisonRequest;
import br.com.checkdiff.domain.ComparisonResponseDomain;
import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.SaveComparisonResultGateway;
import br.com.checkdiff.gateway.exception.SaveComparisonException;
import br.com.checkdiff.gateway.translator.ComparisonResultDomainToComparisonResponseDomainTranslator;
import br.com.checkdiff.usecase.exception.DataEqualsException;
import br.com.checkdiff.usecase.exception.DifferentSizesException;
import br.com.checkdiff.usecase.exception.InvalidJsonException;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class CheckDiffUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDiffUseCase.class);

    private final ConvertBase64ToJsonUseCase convertBase64ToJsonUseCase;
    private final SaveComparisonResultGateway saveComparisonResultGateway;

    public CheckDiffUseCase(ConvertBase64ToJsonUseCase convertBase64ToJsonUseCase, SaveComparisonResultGateway saveComparisonResultGateway) {
        this.convertBase64ToJsonUseCase = convertBase64ToJsonUseCase;
        this.saveComparisonResultGateway = saveComparisonResultGateway;
    }

    /**
     * checks differences between two json's
     * @param comparisonRequest
     * @return
     * @throws DataEqualsException
     * @throws DifferentSizesException
     * @throws InvalidJsonException
     * @throws SaveComparisonException
     */
    public Mono<ComparisonResponseDomain> execute(ComparisonRequest comparisonRequest) throws DataEqualsException, InvalidJsonException, SaveComparisonException {
        if (comparisonRequest.getLeft().equals(comparisonRequest.getRight())) throw new DataEqualsException("data are equals");

        Gson g = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();

        String leftDecodedJson = convertBase64ToJsonUseCase.execute(comparisonRequest.getLeft());
        String rightDecodedJson = convertBase64ToJsonUseCase.execute(comparisonRequest.getRight());

        Map<String, Object> left = g.fromJson(leftDecodedJson, mapType);
        Map<String, Object> right = g.fromJson(rightDecodedJson, mapType);

        LOGGER.info("mapping differences between json");
        Set<String> onlyOnLeft = Maps.difference(left, right).entriesOnlyOnLeft().keySet();
        Set<String> onlyOnRight = Maps.difference(left, right).entriesOnlyOnRight().keySet();
        Map<String, MapDifference.ValueDifference<Object>> differentValue = Maps.difference(left, right).entriesDiffering();

        ComparisonResultDomain result = ComparisonResultDomain.builder()
                .leftData(leftDecodedJson)
                .rightData(rightDecodedJson)
                .onlyLeftFields(onlyOnLeft)
                .onlyRightFields(onlyOnRight)
                .differentFields(g.toJson(differentValue))
                .build();

        LOGGER.info("saving comparison of results");
        return saveComparisonResultGateway.save(result)
                .map(ComparisonResultDomainToComparisonResponseDomainTranslator::translate);
    }

}
