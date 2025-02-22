import adapter.http.RestHandler;
import adapter.http.ResponseSender;
import adapter.rest.HelloHandler;
import adapter.rest.TimeHandler;
import com.sun.net.httpserver.HttpServer;
import di.ApplicationContext;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executor;

public class Application {
    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    private static HttpServer server;

    public static void main(String[] args) throws Exception {
        // TODO (jdb): refactor to finding classes extending HttpHandler on startup and querying the path from them
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

        ApplicationContext.register("responseSender", ResponseSender.class, new ResponseSender());

        ApplicationContext.register("helloHandler", HelloHandler.class,
                new HelloHandler(ApplicationContext.findBean(ResponseSender.class)));
        ApplicationContext.register("timeHandler", TimeHandler.class,
                new TimeHandler(ApplicationContext.findBean(ResponseSender.class)));

        ApplicationContext.register("router", Router.class, new Router(
                ApplicationContext.findBean(HttpServer.class),
                List.of(ApplicationContext.findBean(HelloHandler.class),
                        ApplicationContext.findBean(TimeHandler.class))));
    }

    private static void run() {
        server.start();
        System.out.printf("Application running, server listening on port %s%n", PORT);
    }

}
