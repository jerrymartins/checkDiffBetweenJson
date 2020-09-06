package br.com.checkdiff.controller

import br.com.checkdiff.usecase.CheckDiffUseCase
import br.com.checkdiff.usecase.FindComparisonResultUseCase
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

    def 'must not accept request without path variable id'() {
        when: 'rest account url is hit'
        def response = mockMvc.perform(get('/check-diff/')).andReturn().response

        then: 'must launch http status code 404 when there is no path variable'
        response.status == METHOD_NOT_ALLOWED.value()
    }
}
