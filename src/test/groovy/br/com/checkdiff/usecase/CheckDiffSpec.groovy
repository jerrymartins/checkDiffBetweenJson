package br.com.checkdiff.usecase

import br.com.checkdiff.domain.ComparisonRequest
import br.com.checkdiff.domain.ComparisonResultDomain
import br.com.checkdiff.gateway.SaveComparisonResultGateway
import br.com.checkdiff.usecase.exception.DataEqualsException
import reactor.core.publisher.Mono
import spock.lang.Specification

class CheckDiffSpec extends Specification {
    ConvertBase64ToJsonUseCase convertBase64ToJsonUseCase = Mock(ConvertBase64ToJsonUseCase)
    SaveComparisonResultGateway saveComparisonResultGateway = Mock(SaveComparisonResultGateway.class)

    CheckDiffUseCase checkDiffUseCase = new CheckDiffUseCase(convertBase64ToJsonUseCase, saveComparisonResultGateway)

    def 'must identify that jsons are equal and throw an exception'() {
        given: 'a request objet with json equals'
        ComparisonRequest comparisonRequest = ComparisonRequest.builder()
                .left('eyJuYW1lIjoiQUJDZCIsICJjaXR5IjoiWFlaIiwgInN0YXRlIjoiQ0EifQ==')
                .right('eyJuYW1lIjoiQUJDZCIsICJjaXR5IjoiWFlaIiwgInN0YXRlIjoiQ0EifQ==')
                .build()

        when: 'usecase is called'
        checkDiffUseCase.execute(comparisonRequest)

        then: 'must throw DataEqualsException'
        thrown(DataEqualsException)
    }

    def 'must identify properties that exist only left'() {
        given: 'a request objet with valid json'
        ComparisonRequest comparisonRequest = ComparisonRequest.builder()
                .left('ewogICAgInR5cGUiOiAiY2hhbGVuZ2UiLAogICAgImxldmVsIjogNiwKICAgICJjYXRlZ29yeSI6ICJkZXZlbG9wbWVudCIsCiAgICAibGFuZ3VhZ2UiOiAiamF2YSIKfQ==')
                .right('ewogICAgInR5cGUiOiAiY2hhbGVuZ2UiLAogICAgImxldmVsIjogNgp9')
                .build()

        when: 'usecase is called'
        checkDiffUseCase.execute(comparisonRequest)

        then: 'gateway is called'
        saveComparisonResultGateway.save(_ as ComparisonResultDomain) >> Mono.just(buildComparisonResultDomain())

        and: 'use case is called to convert left'
        convertBase64ToJsonUseCase.execute(comparisonRequest.getLeft())  >> {
            return '{\n' +
                    '    "type": "chalenge",\n' +
                    '    "level": 6,\n' +
                    '    "category": "development",\n' +
                    '    "language": "java"\n' +
                    '}'
        }

        and: 'use case is called to convert right'
        convertBase64ToJsonUseCase.execute(comparisonRequest.getRight()) >> {
            return '{\n' +
                    '    "type": "chalenge",\n' +
                    '    "level": 6\n' +
                    '}'
        }

        and: 'checks whether properties existing only on the left have been collected'
        0 * saveComparisonResultGateway.save({ ComparisonResultDomain comparison -> comparison.getOnlyLeftFields().containsAll( Arrays.asList('category', 'language'))})
    }

    def 'must identify properties that exist only right'() {
        given: 'a request objet with valid json'
        ComparisonRequest comparisonRequest = ComparisonRequest.builder()
                .left('ewogICAgInR5cGUiOiAiY2hhbGVuZ2UiLAogICAgImxldmVsIjogNgp9')
                .right('ewogICAgInR5cGUiOiAiY2hhbGVuZ2UiLAogICAgImxldmVsIjogNiwKICAgICJjYXRlZ29yeSI6ICJkZXZlbG9wbWVudCIsCiAgICAibGFuZ3VhZ2UiOiAiamF2YSIKfQ==')
                .build()

        when: 'usecase is called'
        checkDiffUseCase.execute(comparisonRequest)

        then: 'gateway is called'
        saveComparisonResultGateway.save(_ as ComparisonResultDomain) >> Mono.just(buildComparisonResultDomain())

        and: 'use case is called to convert left'
        convertBase64ToJsonUseCase.execute(comparisonRequest.getLeft())  >> {
            return '{\n' +
                    '    "type": "chalenge",\n' +
                    '    "level": 6,\n' +
                    '    "category": "development",\n' +
                    '    "language": "java"\n' +
                    '}'
        }

        and: 'use case is called to convert right'
        convertBase64ToJsonUseCase.execute(comparisonRequest.getRight()) >> {
            return '{\n' +
                    '    "type": "chalenge",\n' +
                    '    "level": 6\n' +
                    '}'
        }

        and: 'checks whether properties existing only on the left have been collected'
        0 * saveComparisonResultGateway.save({ ComparisonResultDomain comparison -> comparison.getOnlyRightFields().containsAll( Arrays.asList())})
    }

    private static ComparisonResultDomain buildComparisonResultDomain() {
        return ComparisonResultDomain.builder()
                .leftData('xpto')
                .rightData('xpto')
                .onlyLeftFields(new HashSet<String>())
                .onlyRightFields(new HashSet<String>())
                .differentFields('xpto')
                .build()

    }

}