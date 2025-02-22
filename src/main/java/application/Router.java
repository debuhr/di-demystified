package application;

import application.adapter.rest.RestHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.List;

/**
 * Routes from request paths to HttpHandler classes are defined in this class.
 */
public class Router {
    private final HttpServer httpServer;
    private final List<RestHandler> httpHandlers;

    public Router(HttpServer httpServer, List<RestHandler> httpHandlers) {
        this.httpServer = httpServer;
        this.httpHandlers = httpHandlers;
        initRoutes();
    }

    private void initRoutes() {
        httpHandlers.forEach(this::defineRoute);
    }

    public void defineRoute(RestHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be NULL");
        }
        if (handler.getPath() == null) {
            throw new IllegalArgumentException("Path cannot be NULL");
        }

        httpServer.createContext(handler.getPath(), handler);
    }
}
