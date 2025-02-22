package adapter.rest;

import adapter.http.HttpStatus;
import adapter.http.RestHandler;
import adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class TimeHandler implements RestHandler {
    private final ResponseSender responseSender;

    @Getter
    private final String path = "/time";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        responseSender.sendResponse(exchange, response, HttpStatus.OK);
    }
}
