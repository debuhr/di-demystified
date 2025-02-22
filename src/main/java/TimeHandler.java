import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class TimeHandler implements HttpHandler {
    private final ResponseSender responseSender;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        responseSender.sendResponse(exchange, response, HttpStatus.OK);
    }
}
