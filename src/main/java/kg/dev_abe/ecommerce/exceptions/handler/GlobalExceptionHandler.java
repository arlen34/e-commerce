package kg.dev_abe.ecommerce.exceptions.handler;


import kg.dev_abe.ecommerce.exceptions.BadRequestException;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.exceptions.ExceptionResponse;
import kg.dev_abe.ecommerce.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerNotFoundException(BadRequestException e){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException e){
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
    }
    @ExceptionHandler(ECommerceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerEcommerceException(ECommerceException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,e.getClass().getSimpleName(),e.getMessage());

    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse globalHandler(Exception e){
        log.error(e.getMessage(), e);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getSimpleName(), e.getMessage());
    }

}
