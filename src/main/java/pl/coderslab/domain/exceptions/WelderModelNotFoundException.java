package pl.coderslab.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WelderModelNotFoundException extends RuntimeException {

    public WelderModelNotFoundException(Long id) {
        super("Model with id " + id + " not found");
    }
}
