import adapter.http.RestHandler
import adapter.http.ResponseSender
import adapter.rest.HelloHandler
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import spock.lang.Specification

class RouterTest extends Specification {
    HttpServer server = Mock(HttpServer)
    ResponseSender responseSender = new ResponseSender()
    HelloHandler helloHandler = new HelloHandler(responseSender)

    def "Router can be created"() {
        when: "a router is created"
        Router router = new Router(this.server, [helloHandler])

        then: "the instance can be created"
        noExceptionThrown()
        router != null
    }

    def "Router defines routes on the server"() {
        when:
        Router router = new Router(this.server, [helloHandler])

        then:
        1 * server.createContext("/hello", _ as HelloHandler)
    }

    class LukeHandler implements RestHandler {
        @Override
        void handle(HttpExchange httpExchange) throws IOException {}

        @Override
        String getPath() {
            return "/aNewHope"
        }
    }

    def "Routes can be defined"() {
        given: "a HttpHandler"
        def lukeHandler = new LukeHandler()

        when:
        Router router = new Router(this.server, [helloHandler])
        router.defineRoute(lukeHandler)

        then:
        1 * server.createContext("/aNewHope", lukeHandler)
    }

    def "It is not possible to define a handler for a null path"() {
        when: "null is given as a path"
        Router router = new Router(this.server, [helloHandler])
        router.defineRoute(new LukeHandler() {
            @Override
            String getPath() {
                return null
            }
        })

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }

    def "It is not possible to define a null handler"() {
        when: "null is given as a handler"
        Router router = new Router(this.server, [helloHandler])
        router.defineRoute(null)

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }
}
