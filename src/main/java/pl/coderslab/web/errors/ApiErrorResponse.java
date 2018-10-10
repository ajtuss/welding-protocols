package pl.coderslab.web.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private Map<String, Object> parameters;


    public ApiErrorResponse(HttpStatus status, Throwable ex) {
        this(status, ex, null);
    }

    public ApiErrorResponse(HttpStatus status, Throwable ex, Map<String, Object> parameters) {
        this(status, ex.getMessage(), ex, parameters);
    }

    public ApiErrorResponse(HttpStatus status, String message, Throwable ex, Map<String, Object> parameters) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.parameters = Optional.ofNullable(parameters).orElseGet(LinkedHashMap::new);
    }


    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}