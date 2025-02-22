import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum HttpStatus {
    OK(200);

    @Getter
    private final int code;
}
