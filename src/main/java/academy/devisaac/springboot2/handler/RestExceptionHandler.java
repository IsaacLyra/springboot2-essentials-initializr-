package academy.devisaac.springboot2.handler;

import academy.devisaac.springboot2.exception.BadRequestException;
import academy.devisaac.springboot2.exception.BadRequestExceptionDetails;
import academy.devisaac.springboot2.exception.ExceptionDetails;
import academy.devisaac.springboot2.exception.ValidationExceptionDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // todos os controllers tem que utilizar o que esta dentro dessa classe
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private MessageSource messageSource;

    @ExceptionHandler(BadRequestException.class)//Caso tenha uma exception retorne o badRequest
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestExceptionDetails  handleBadRequestException(BadRequestException badRequestException){
        return BadRequestExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, Check the documentation")
                .details(badRequestException.getMessage())
                .developerMessage(badRequestException.getClass().getName())
                .build();
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
            String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
            String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

            return new ResponseEntity<>(ValidationExceptionDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .title("Bad Request Exception, Invalid fields")
                    .details("Check the fields error")
                    .developerMessage(exception.getClass().getName())
                    .fields(fields)
                    .fieldsMessage(fieldsMessage)
                    .build(), HttpStatus.BAD_REQUEST);
        }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Response already committed. Ignoring: " + ex);
                }
                return null;
            }
        }

        if (body == null && ex instanceof ErrorResponse errorResponse) {
            body = errorResponse.updateAndGetBody(this.messageSource, LocaleContextHolder.getLocale());
        }

     ExceptionDetails exceptionDetails =   ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode.value())
                .title(ex.getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return createResponseEntity(exceptionDetails, headers, statusCode, request);
    }



}

