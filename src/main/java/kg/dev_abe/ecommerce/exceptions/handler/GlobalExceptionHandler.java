package kg.dev_abe.ecommerce.exceptions.handler;


import kg.dev_abe.ecommerce.exceptions.BadRequestException;
import kg.dev_abe.ecommerce.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerNotFoundException(BadRequestException e){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());
    }
}
