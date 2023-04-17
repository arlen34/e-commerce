package kg.dev_abe.ecommerce.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException() {
    }
}
