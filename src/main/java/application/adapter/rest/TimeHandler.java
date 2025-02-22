package application.adapter.rest;

import application.adapter.http.HttpStatus;
import application.adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpExchange;
import di.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
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
