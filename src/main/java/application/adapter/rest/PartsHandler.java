package application.adapter.rest;

import application.domain.Part;
import application.domain.PartsRepository;
import application.RestHandler;
import application.adapter.http.HttpStatus;
import application.adapter.http.ResponseSender;
import com.sun.net.httpserver.HttpExchange;
import di.annotation.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PartsHandler implements RestHandler {
    private final ResponseSender responseSender;
    private final PartsRepository partsRepository;

    @Getter
    private final String path = "/parts";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = partsRepository.findAll().stream()
                .map(Part::getName)
                .map(name -> "\"" + name + "\"")
                .collect(Collectors.joining("\n"));

        responseSender.sendResponse(exchange, response, HttpStatus.OK);
    }
}
