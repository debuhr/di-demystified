package application.adapter.rest


import application.adapter.datastore.StaticPartsRepository
import application.adapter.http.HttpStatus
import application.adapter.http.ResponseSender
import application.domain.PartsRepository
import com.sun.net.httpserver.HttpExchange
import spock.lang.Specification

class PartsHandlerTest extends Specification {
    ResponseSender responseSender = Mock(ResponseSender)
    PartsRepository partsRepository = new StaticPartsRepository()
    PartsHandler partsHandler = new PartsHandler(responseSender, partsRepository)

    String parts = '''"steering wheel"
"connecting rod"
"suspension spring"
"spark plug"'''

    def "Parts handler returns a list of parts"() {
        when: "the parts handler is called"
        partsHandler.handle(Mock(HttpExchange))

        then: "a list of parts is sent as a response"
        1 * responseSender.sendResponse(_, parts, HttpStatus.OK)
    }
}
