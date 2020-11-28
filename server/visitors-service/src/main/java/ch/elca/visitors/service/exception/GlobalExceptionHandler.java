package ch.elca.visitors.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


//     handle specific exceptions
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                exception.getMessage(),
                LocalDateTime.now(),
                notFound
        );

        return new ResponseEntity<>(exceptionDetails, notFound);
    }


//     handle specific exceptions
//    @ExceptionHandler(value = {ApiRequestException.class})
//    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception, WebRequest webRequest) {
//
//        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
//
//        ExceptionDetails exceptionDetails = new ExceptionDetails(
//                exception.getMessage(),
//                LocalDateTime.now(),
//                badRequest
//        );
//
//        return new ResponseEntity<>(exceptionDetails, badRequest);
//    }


//     handle global exceptions
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception exception, WebRequest webRequest) {

        log.error("Error occurred", exception);

        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        // Payload containing exception details
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                exception.getMessage(),
                LocalDateTime.now(),
                internalServerError
        );

        return new ResponseEntity<>(exceptionDetails, internalServerError);
    }
}
