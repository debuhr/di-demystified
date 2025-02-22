package application.adapter.rest;

import application.adapter.http.HttpStatus;
import application.adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpExchange;
import di.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
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
