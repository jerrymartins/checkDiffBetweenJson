package br.com.checkdiff.controller

import br.com.checkdiff.domain.ComparisonResultDomain
import br.com.checkdiff.gateway.exception.FindComparisonException
import br.com.checkdiff.gateway.exception.NotFoundComparisonException
import br.com.checkdiff.usecase.CheckDiffUseCase
import br.com.checkdiff.usecase.FindComparisonResultUseCase
import reactor.core.publisher.Mono
import spock.lang.Specification
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.http.HttpStatus.*

class CheckDiffControllerTest extends Specification {
    CheckDiffUseCase checkDiffUseCase = Mock(CheckDiffUseCase)
    FindComparisonResultUseCase findComparisonResultUseCase = Mock(FindComparisonResultUseCase)

    CheckDiffController controller = new CheckDiffController(checkDiffUseCase, findComparisonResultUseCase)

    MockMvc mockMvc = standaloneSetup(controller).build()

    def 'must not accept request without path variable'() {
        when: 'rest check-diff url is hit'
        def response = mockMvc.perform(get('/check-diff/')).andReturn().response

        then: 'must launch http status code 404 when there is no path variable'
        response.status == METHOD_NOT_ALLOWED.value()
    }

    def 'must not accept request with incorrect type in path variable'() {
        when: 'rest check-diff url is hit'
        def response = mockMvc.perform(get('/check-diff/B')).andReturn().response

        then: 'must launch http status code 400 when there is no path variable'
        response.status == BAD_REQUEST.value()
    }

    def 'must return a result when the parameter for valid and result of the comparison exists in the database'() {
        when: 'rest check-diff url is hit'
        def response = mockMvc.perform(get('/check-diff/1')).andReturn().getAsyncResult()

        then: 'use case is called and return the correct data'
        findComparisonResultUseCase.execute(_ as Long) >> Mono.just(ComparisonResultDomain.builder().leftData('xpto').rightData('xpto').id(1).build())

        and: 'data must be returned correctly'
        response.id == 1

    }

    def 'should throw an exception if a search error occurs'() {
        when: 'rest check-diff url is hit'
        def response = mockMvc.perform(get('/check-diff/1000')).andReturn().response

        then: 'use case is called and throw an exception'
        findComparisonResultUseCase.execute(_ as Long) >> {
            throw new FindComparisonException('some message')
        }

        and: 'an exception must be returned'
        response.status == BAD_REQUEST.value()

    }

    def 'should throw an exception if no data is found'() {
        when: 'rest check-diff url is hit'
        def response = mockMvc.perform(get('/check-diff/1000')).andReturn().response

        then: 'use case is called and throw an exception'
        findComparisonResultUseCase.execute(_ as Long) >> {
            throw new NotFoundComparisonException('some message')
        }

        and: 'must be http status code 404'
        response.status == NOT_FOUND.value()
    }
}
