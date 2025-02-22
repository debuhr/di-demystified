package application.adapter.http;

import com.sun.net.httpserver.HttpExchange;
import di.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Writes a response string to the HttpExchange and sets the status code and response length.
 */
@Component
public class ResponseSender {
    public void sendResponse(HttpExchange exchange, String response, HttpStatus status) throws IOException {
        exchange.sendResponseHeaders(status.getCode(), response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
