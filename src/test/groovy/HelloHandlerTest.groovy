import com.sun.net.httpserver.HttpExchange
import spock.lang.Specification

class HelloHandlerTest extends Specification {
    HelloHandler helloHandler = new HelloHandler()

    def "HelloHandler returns hello message"() {
        given: "an HttpExchange that captures the response body in an output Stream"
        HttpExchange exchange = Mock(HttpExchange)
        ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream()
        exchange.getResponseBody() >> responseOutputStream

        when: "the handler is called"
        helloHandler.handle(exchange)

        then: "the hello message is written to the output stream"
        responseOutputStream.toString() == "Hello, this is a simple HTTP server response!\n\n"
    }
}
