package adapter.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpStatus {
    OK(200);

    @Getter
    private final int code;
}
