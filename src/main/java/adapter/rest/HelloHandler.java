package adapter.rest;

import adapter.http.HttpStatus;
import adapter.http.RestHandler;
import adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class HelloHandler implements RestHandler {
    private final ResponseSender responseSender;

    @Getter
    private final String path = "/hello";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello, this is a simple HTTP server response!\n\n";
        responseSender.sendResponse(exchange, response, HttpStatus.OK);
    }
}
