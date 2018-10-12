package pl.coderslab.web.errors;

import java.util.HashMap;
import java.util.Map;

public class BadRequestException extends RuntimeException {


    private final String errorKey;

    private Map<String, Object> params;

    public BadRequestException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage);
        this.params = getAlertParameters(entityName);
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }


    public Map<String, Object> getParams() {
        return this.params;
    }

    private static Map<String, Object> getAlertParameters(String entityName) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("params", entityName);
        return parameters;
    }
}
