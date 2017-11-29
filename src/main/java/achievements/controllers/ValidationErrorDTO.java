package achievements.controllers;

import org.springframework.validation.FieldError;

public class ValidationErrorDTO {
    private String message;
    private String fieldName;
    private String objectName;

    public ValidationErrorDTO() {
        super();
    }

    public ValidationErrorDTO(String objectName, String fieldName , String message) {
        super();
        this.message = message;
        this.objectName = objectName;
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
