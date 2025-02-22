import com.sun.net.httpserver.HttpExchange
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class TimeHandlerTest extends Specification {
    ResponseSender responseSender = new ResponseSender()
    TimeHandler timeHandler = new TimeHandler(responseSender)

    def "Time handler returns current date and time"() {
        given: "an HttpExchange that captures the response body in an output Stream"
        HttpExchange exchange = Mock(HttpExchange)
        ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream()
        exchange.getResponseBody() >> responseOutputStream

        when: "the handler is called"
        timeHandler.handle(exchange)

        then: "the current date and time are written to the output stream"
        LocalDateTime responseDateTime = LocalDateTime.parse(responseOutputStream.toString())
        long timeDiffMinutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), responseDateTime)
        timeDiffMinutes < 1
    }
}
