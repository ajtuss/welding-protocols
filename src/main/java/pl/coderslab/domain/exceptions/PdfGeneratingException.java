package pl.coderslab.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfGeneratingException extends RuntimeException{
    public PdfGeneratingException() {
        super("Cant generate pdf file.");
    }
}
