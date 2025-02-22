import adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Application {
    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    private static HttpServer server;

    public static void main(String[] args) throws Exception {
        // TODO (jdb): add dependency injection and refactor Application and Router to use it
        // --
        // TODO (jdb): refactor to finding classes extending HttpHandler on startup and querying the path from them
        // TODO (jdb): refactor to use annotations to define handlers
        // TODO (jdb): mark GET and POST handlers, maybe with annotations and reject wrong requests
        // TODO (jdb): add JSON response type (and maybe HTML or XML - something to force the code to be flexible)

        initApplicationContext();
        // TODO (jdb): this hurts, there has to be a better way to inject the dependencies of the application
        server = (HttpServer) ApplicationContext.findBean("httpServer");
        Application.run();
    }

    private static void initApplicationContext() throws Exception {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.setExecutor(DEFAULT_EXECUTOR); // TODO (jdb): this should happen during bean creation
        ApplicationContext.register("httpServer", httpServer);

        ApplicationContext.register("responseSender", new ResponseSender());
        ApplicationContext.register("router", new Router(
                (HttpServer) ApplicationContext.findBean("httpServer"),
                (ResponseSender) ApplicationContext.findBean("responseSender")));
    }

    private static void run() {
        server.start();
        System.out.printf("Application running, server listening on port %s%n", PORT);
    }

}
