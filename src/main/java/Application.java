import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Application {

    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    public static void main(String[] args) throws IOException {
        Application.run();
    }

    public static void run() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // TODO (jdb): create a router or a different way to register mare than one handler
        server.createContext("/", new MyHandler());
        server.setExecutor(DEFAULT_EXECUTOR);
        server.start();

        System.out.printf("Application running, server listening on port %s%n", PORT);
    }

    // define a custom HttpHandler
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            String response = "Hello, this is a simple HTTP server response!\n\n";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
