package application;

import com.sun.net.httpserver.HttpServer;
import di.ApplicationContext;

public class Application {
    private static HttpServer server;

    public static void main(String[] args) throws Exception {
        initApplicationContext();
        Application.run();
    }

    private static void initApplicationContext() throws Exception {
        ApplicationContext.scanPackage("application");
        server = ApplicationContext.findBean(HttpServer.class);
    }

    private static void run() {
        server.start();
        System.out.printf("application.Application running, server listening on port %s%n", AppConfiguration.PORT);
    }

}
