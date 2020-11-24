package ch.elca.visitors.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExceptionDetails {

    private final String message;
    private final LocalDateTime timestamp;
    private final HttpStatus httpStatus;

}
