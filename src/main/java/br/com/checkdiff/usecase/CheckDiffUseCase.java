package br.com.checkdiff.usecase;

import br.com.checkdiff.domain.ComparisonRequestDomain;
import br.com.checkdiff.domain.ComparisonResponseDomain;
import br.com.checkdiff.domain.ComparisonResultDomain;
import br.com.checkdiff.gateway.SaveComparisonResultGateway;
import br.com.checkdiff.gateway.translator.ComparisonResultDomainToComparisonResponseDomainTranslator;
import br.com.checkdiff.usecase.exception.CheckDiffException;
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

    private final SaveComparisonResultGateway saveComparisonResultGateway;

    public CheckDiffUseCase(SaveComparisonResultGateway saveComparisonResultGateway) {
        this.saveComparisonResultGateway = saveComparisonResultGateway;
    }

    public Mono<ComparisonResponseDomain> check(ComparisonRequestDomain comparisonRequestDomain) throws CheckDiffException {
        try {
            Gson g = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> left = g.fromJson(decode(comparisonRequestDomain.getLeft()), mapType);
            Map<String, Object> right = g.fromJson(decode(comparisonRequestDomain.getRight()), mapType);

            Set<String> onlyOnLeft = Maps.difference(left, right).entriesOnlyOnLeft().keySet();
            Set<String> onlyOnRight = Maps.difference(left, right).entriesOnlyOnRight().keySet();
            Map<String, MapDifference.ValueDifference<Object>> differentValue = Maps.difference(left, right).entriesDiffering();

            var result = ComparisonResultDomain.builder()
                    .leftData(decode(comparisonRequestDomain.getLeft()))
                    .rightData(decode(comparisonRequestDomain.getRight()))
                    .onlyLeftFields(onlyOnLeft)
                    .onlyRightFields(onlyOnRight)
                    .differentFields(g.toJson(differentValue))
                    .build();

            return saveComparisonResultGateway.save(result).map(ComparisonResultDomainToComparisonResponseDomainTranslator::translate);

        } catch (Exception ex) {
            throw new CheckDiffException("deu ruim");
        }


    }

    private String decode(String stringBase64) throws InvalidJsonException {
        byte[] decodedBytes = Base64.getDecoder().decode(stringBase64);
        String json = new String(decodedBytes);

        if (isValidJson(json)) {
            return json;
        } else {
            throw new InvalidJsonException("algo legal aqui");
        }
    }

    private boolean isValidJson(String json) {
        Gson g = new Gson();
        try {
            g.fromJson(json, Object.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

}
