package pl.coderslab.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ValidProtocolNotFoundException extends RuntimeException {

    public ValidProtocolNotFoundException(Long id) {
        super("Protocol with id " + id + " not found");
    }
}
