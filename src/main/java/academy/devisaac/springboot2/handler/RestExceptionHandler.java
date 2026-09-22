package academy.devisaac.springboot2.handler;

import academy.devisaac.springboot2.exception.BadRequestException;
import academy.devisaac.springboot2.exception.BadRequestExceptionDetails;
import academy.devisaac.springboot2.exception.ValidationExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // todos os controllers tem que utilizar o que esta dentro dessa classe
@Log4j2
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
        @ExceptionHandler(MethodArgumentNotValidException.class)//Caso tenha uma exception retorne o badRequest
        @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDetails handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
            List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
            String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
            String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

            return ValidationExceptionDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .title("Bad Request Exception, Invalid fields")
                    .details("Check the fields error")
                    .developerMessage(exception.getClass().getName())
                    .fields(fields)
                    .fieldsMessage(fieldsMessage)
                    .build();
        }
    }

