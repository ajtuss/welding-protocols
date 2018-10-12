package pl.coderslab.web.errors;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class NotFoundException extends RuntimeException {

    private Map<String, Object> params;
    private HttpStatus status;

    public NotFoundException(String path, String entityName) {
        super(String.format("%s not found", entityName));
        this.params = getAlertParameters(entityName, path);
        this.status = HttpStatus.NOT_FOUND;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public HttpStatus getStatus() {
        return status;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String path) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("objectName", entityName);
        parameters.put("path", path);
        return parameters;
    }

}
