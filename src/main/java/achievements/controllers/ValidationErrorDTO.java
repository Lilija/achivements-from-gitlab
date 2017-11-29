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

    public String getFieldName() {
        return fieldName;
    }

    public String getObjectName() {
        return objectName;
    }

}
