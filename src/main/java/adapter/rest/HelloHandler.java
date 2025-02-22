package adapter.rest;

import adapter.http.HttpStatus;
import adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class HelloHandler implements HttpHandler {
    private final ResponseSender responseSender;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello, this is a simple HTTP server response!\n\n";
        responseSender.sendResponse(exchange, response, HttpStatus.OK);
    }
}
