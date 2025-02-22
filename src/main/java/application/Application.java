package application;

import com.sun.net.httpserver.HttpServer;
import di.ApplicationContext;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Application {
    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    private static HttpServer server;

    public static void main(String[] args) throws Exception {
        // TODO (jdb): refactor to use annotations to define handlers
        // TODO (jdb): mark GET and POST handlers, maybe with annotations and reject wrong requests
        // TODO (jdb): add JSON response type (and maybe HTML or XML - something to force the code to be flexible)

        initApplicationContext();
        // TODO (jdb): this hurts, there has to be a better way to inject the dependencies of the application
        server = ApplicationContext.findBean(HttpServer.class);
        Application.run();
    }

    private static void initApplicationContext() throws Exception {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.setExecutor(DEFAULT_EXECUTOR);
        ApplicationContext.register("httpServer", HttpServer.class, httpServer);
        ApplicationContext.scanPackage("application");
    }

    private static void run() {
        server.start();
        System.out.printf("application.Application running, server listening on port %s%n", PORT);
    }

}
