import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import spock.lang.Specification

class RouterTest extends Specification {
    HttpServer server = Mock(HttpServer)

    def "Router can be created"() {
        when: "a router is created"
        Router router = new Router(this.server)

        then: "the instance can be created"
        noExceptionThrown()
        router != null
    }

    def "Router defines routes on the server"() {
        when:
        Router router = new Router(this.server)

        then:
        1 * server.createContext("/hello", _ as HelloHandler)
    }

    class LukeHandler implements HttpHandler {
        @Override
        void handle(HttpExchange httpExchange) throws IOException {

        }
    }

    def "Routes can be defined"() {
        given: "a HttpHandler"
        def lukeHandler = new LukeHandler()

        when:
        Router router = new Router(this.server)
        router.defineRoute("/aNewHope", lukeHandler)

        then:
        1 * server.createContext("/aNewHope", lukeHandler)
    }

    def "It is not possible to define a handler for a null path"() {
        when: "null is given as a path"
        Router router = new Router(this.server)
        router.defineRoute(null, new LukeHandler())

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }

    def "It is not possible to define a null handler"() {
        when: "null is given as a handler"
        Router router = new Router(this.server)
        router.defineRoute("/aNewHope", null)

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }
}
