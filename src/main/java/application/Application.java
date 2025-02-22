package application;

import com.sun.net.httpserver.HttpServer;
import di.ApplicationContext;

/**
 * This is the main class of the application. It initializes the application context by starting the component scan
 * across the application package. After the application context is initialized, it starts the HTTP server.
 */
public class Application {
    private static HttpServer server;

    public static void main(String[] args) {
        initApplicationContext();
        Application.run();
    }

    private static void initApplicationContext() {
        ApplicationContext.scanPackage("application");
        server = ApplicationContext.findBean(HttpServer.class);
    }

    private static void run() {
        server.start();
        System.out.printf("application.Application running, server listening on port %s%n", AppConfiguration.PORT);
    }

}
