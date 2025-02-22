import adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Application {
    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    public static void main(String[] args) throws IOException {
        // TODO (jdb): add dependency injection and refactor Application and Router to use it
        // --
        // TODO (jdb): refactor to finding classes extending HttpHandler on startup and querying the path from them
        // TODO (jdb): refactor to use annotations to define handlers
        // TODO (jdb): mark GET and POST handlers, maybe with annotations and reject wrong requests
        // TODO (jdb): add JSON response type (and maybe HTML or XML - something to force the code to be flexible)

        Application.run();
    }

    public static void run() throws IOException {
        // TODO (jdb): the run method has the responsibility of creating the instances of the classes that make up the
        //  running application. This is not good, we should separate this concept and call it from main.
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        ResponseSender responseSender = new ResponseSender();
        Router router = new Router(server, responseSender);

        server.setExecutor(DEFAULT_EXECUTOR);
        server.start();

        System.out.printf("Application running, server listening on port %s%n", PORT);
    }

}
