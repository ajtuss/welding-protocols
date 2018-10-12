package pl.coderslab.web.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private String title;
    private HttpStatus status;
    private String message;
    private Map<String, Object> parameters;


    public ApiErrorResponse(String title, HttpStatus status, String message, Map<String, Object> parameters) {
        this.timestamp = LocalDateTime.now();
        this.title = title;
        this.status = status;
        this.message = message;
        this.parameters = Optional.ofNullable(parameters).orElseGet(LinkedHashMap::new);
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

}