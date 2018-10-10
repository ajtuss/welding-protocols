package pl.coderslab.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeasureNotFoundException extends RuntimeException {

    public MeasureNotFoundException(Long id) {
        super("Measure with id " + id + " not found");
    }
}
