package br.com.checkdiff.controller

import br.com.checkdiff.domain.ComparisonRequest
import br.com.checkdiff.domain.ComparisonResultDomain
import br.com.checkdiff.usecase.CheckDiffUseCase
import br.com.checkdiff.usecase.FindComparisonResultByIdUseCase
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import reactor.core.publisher.Mono
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CheckDiffControllerTest extends Specification {

    CheckDiffUseCase checkDiffUseCase = Mock(CheckDiffUseCase)

    CheckDiffController controller = new CheckDiffController(checkDiffUseCase)

    MockMvc mockMvc = standaloneSetup(controller).build()

    def 'must not accept request with empty request body'() {
        given: 'a request valid'
        ComparisonRequest request = ComparisonRequest.builder().build()

        when: 'rest check-diff url is hit'
        def response = mockMvc.perform(
                post('/check-diff')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToBytes(request))).andReturn().getResponse()

        then: 'must be return bad request'
        response.status == BAD_REQUEST.value()
    }

    private static byte[] objToBytes(ComparisonRequest request) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream()
        ObjectOutputStream oos = new ObjectOutputStream(bos)
        oos.writeObject(request)
        oos.flush()
        return bos.toByteArray()
    }
}
