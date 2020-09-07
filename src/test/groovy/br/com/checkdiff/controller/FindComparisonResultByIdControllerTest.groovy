package br.com.checkdiff.controller

import br.com.checkdiff.domain.ComparisonResultDomain
import br.com.checkdiff.gateway.exception.FindComparisonException
import br.com.checkdiff.gateway.exception.NotFoundComparisonException
import br.com.checkdiff.usecase.FindComparisonResultByIdUseCase
import org.springframework.test.web.servlet.MockMvc
import reactor.core.publisher.Mono
import spock.lang.Specification

import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class FindComparisonResultByIdControllerTest extends Specification {
    FindComparisonResultByIdUseCase findComparisonResultUseCase = Mock(FindComparisonResultByIdUseCase)

    FindComparisonResultByIdController controller = new FindComparisonResultByIdController(findComparisonResultUseCase)

    MockMvc mockMvc = standaloneSetup(controller).build()

    def 'must not accept request without path variable'() {
        when: 'rest find-comparison url is hit'
        def response = mockMvc.perform(get('/find-comparison/')).andReturn().response

        then: 'must launch http status code 404 when there is no path variable'
        response.status == NOT_FOUND.value()
    }

    def 'must not accept request with incorrect type in path variable'() {
        when: 'rest find-comparison url is hit'
        def response = mockMvc.perform(get('/find-comparison/B')).andReturn().response

        then: 'must launch http status code 400 when there is no path variable'
        response.status == BAD_REQUEST.value()
    }

    def 'must return a result when the parameter for valid and result of the comparison exists in the database'() {
        when: 'rest find-comparison url is hit'
        def response = mockMvc.perform(get('/find-comparison/1')).andReturn().getAsyncResult()

        then: 'use case is called and return the correct data'
        findComparisonResultUseCase.execute(_ as Long) >> Mono.just(ComparisonResultDomain.builder().leftData('xpto').rightData('xpto').id(1).build())

        and: 'data must be returned correctly'
        response.id == 1

    }

    def 'should throw an exception if a search error occurs'() {
        when: 'rest find-comparison url is hit'
        def response = mockMvc.perform(get('/find-comparison/1000')).andReturn().response

        then: 'use case is called and throw an exception'
        findComparisonResultUseCase.execute(_ as Long) >> {
            throw new FindComparisonException('some message')
        }

        and: 'an exception must be returned'
        response.status == BAD_REQUEST.value()

    }

    def 'should throw an exception if no data is found'() {
        when: 'rest find-comparison url is hit'
        def response = mockMvc.perform(get('/find-comparison/1000')).andReturn().response

        then: 'use case is called and throw an exception'
        findComparisonResultUseCase.execute(_ as Long) >> {
            throw new NotFoundComparisonException('some message')
        }

        and: 'must be http status code 404'
        response.status == NOT_FOUND.value()
    }
}
