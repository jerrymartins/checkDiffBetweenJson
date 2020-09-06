package br.com.checkdiff.usecase

import br.com.checkdiff.gateway.FindComparisonResultGateway
import br.com.checkdiff.gateway.exception.FindComparisonException
import spock.lang.Specification

class FindComparisonSpec extends Specification {
    FindComparisonResultGateway findComparisonResultGateway = Mock(FindComparisonResultGateway)

    FindComparisonResultUseCase findComparisonResultUseCase = new FindComparisonResultUseCase(findComparisonResultGateway)

    def 'must throw exception when finding errors in the search'() {
        given: 'an id comparison'
        Long id = 1l

        when: 'usecase is called'
        findComparisonResultUseCase.execute(id)

        then: 'gateway is called'
        findComparisonResultGateway.findById(id) >> {
            throw new FindComparisonException('some message')
        }

        then: 'must be throw exception not found'
        thrown(FindComparisonException)
    }


}