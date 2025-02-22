package di;

public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException(String message) {
        super(message);
    }
}
