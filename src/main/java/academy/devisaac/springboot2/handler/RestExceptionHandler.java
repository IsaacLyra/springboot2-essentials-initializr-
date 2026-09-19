package academy.devisaac.springboot2.handler;

import academy.devisaac.springboot2.exception.BadRequestException;
import academy.devisaac.springboot2.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice // todos os controllers tem que utilizar o que esta dentro dessa classe
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)//Caso tenha uma exception retorne o badRequest
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestExceptionDetails handlerBadRequestException(BadRequestException badRequestException){
        return BadRequestExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, Check the documentation")
                .details(badRequestException.getMessage())
                .developerMessage(badRequestException.getClass().getName())
                .build();
    }
}
