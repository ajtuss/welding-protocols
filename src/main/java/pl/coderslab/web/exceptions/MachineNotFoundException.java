package pl.coderslab.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MachineNotFoundException extends RuntimeException {

    public MachineNotFoundException(Long id) {
        super("Machine with id " + id + " not found");
    }
}
