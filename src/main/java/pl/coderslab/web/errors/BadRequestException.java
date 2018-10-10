package pl.coderslab.web.errors;

import java.util.HashMap;
import java.util.Map;

public class BadRequestException extends RuntimeException {


    private Map<String, Object> parameters;


    public BadRequestException(String message, String entityName, String errorKey) {
        super(message);
        parameters = getAlertParameters(entityName, errorKey);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
