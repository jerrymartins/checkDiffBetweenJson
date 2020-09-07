package br.com.checkdiff.usecase

import br.com.checkdiff.usecase.exception.InvalidJsonException
import spock.lang.Specification

class ConvertBase64ToJsonSpec extends Specification {

    ConvertBase64ToJsonUseCase convertBase64ToJsonUseCase = new ConvertBase64ToJsonUseCase()

    def 'must be convert string base64 to string json when string json is valid'() {
        given: 'a string base64 with json'
        String base64String = 'ewogICAgInR5cGUiOiAiY2hhbGVuZ2UiLAogICAgImxldmVsIjogNgp9'

        when: 'usecase is called'
        String jsonString = convertBase64ToJsonUseCase.execute(base64String)

        then: 'should thrown exception'
        jsonString.equals('{\n' +
                '    "type": "chalenge",\n' +
                '    "level": 6\n' +
                '}')
    }

    def 'must throw exception if json is invalid'() {
        given: 'a string base64 with invalid json'
        String base64String = 'INVALIDlIjoiQUJDZCIsICJjaXR5IjoiWFlaIiwgInN0YXRlIjoiQ0EifQ=='

        when: 'usecase is called'
        convertBase64ToJsonUseCase.execute(base64String)

        then: 'should thrown exception'
        thrown(InvalidJsonException)
    }

}