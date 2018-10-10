package pl.coderslab.web.errors;

public class FieldError {

    private final String objectName;

    private final String field;

    private final String message;

    public FieldError(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
