import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Application {
    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    public static void main(String[] args) throws IOException {
        // TODO (jdb): refactor to finding classes extending HttpHandler on startup and querying the path from them
        // TODO (jdb): refactor to use annotations to define handlers
        // TODO (jdb): mark GET and POST handlers, maybe with annotations and reject wrong requests
        // TODO (jdb): add JSON response type (and maybe HTML or XML - something to force the code to be flexible)

        Application.run();
    }

    public static void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        Router router = new Router(server);

        server.setExecutor(DEFAULT_EXECUTOR);
        server.start();

        System.out.printf("Application running, server listening on port %s%n", PORT);
    }

}
