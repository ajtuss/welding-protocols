package pl.coderslab.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {
    private final static String MESSAGE = "Id from URL and field must be the same";

    public InvalidIdException() {
        super(MESSAGE);
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
