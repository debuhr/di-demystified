import adapter.http.ResponseSender;
import adapter.rest.HelloHandler;
import adapter.rest.TimeHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Routes from request paths to HttpHandler classes are defined in this class.
 */
public class Router {
    private final HttpServer httpServer;
    private final ResponseSender responseSender;

    public Router(HttpServer httpServer, ResponseSender responseSender) {
        this.httpServer = httpServer;
        this.responseSender = responseSender;
        initRoutes();
    }

    private void initRoutes() {
        // TODO (jdb): Router creates Handler instances? This is bad - it closely couples the Router to the Handlers.
        defineRoute("/hello", new HelloHandler(responseSender));
        defineRoute("/time", new TimeHandler(responseSender));
    }

    public void defineRoute(String path, HttpHandler handler) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be NULL");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be NULL");
        }

        httpServer.createContext(path, handler);
    }
}
