import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Routes from request paths to HttpHandler classes are defined in this class.
 */
public class Router {
    // Dependencies
    private final HttpServer server;
    private final ResponseSender responseSender;

    public Router(HttpServer server, ResponseSender responseSender) {
        this.server = server;
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

        server.createContext(path, handler);
    }
}
