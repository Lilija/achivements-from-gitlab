package achievements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerValidationHandler {

        @Autowired
        private MessageSource msgSource;

        private Locale currentLocale = LocaleContextHolder.getLocale();
        @ResponseBody
        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<List<ValidationErrorDTO>>  processValidationError(MethodArgumentNotValidException ex) {
            BindingResult errorResult = ex.getBindingResult();
            List<FieldError> errorFields = errorResult.getFieldErrors();
            List<ValidationErrorDTO> errors = errorFields.stream()
                                        .map(p->getValidationErrorDto(p))
                                        .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);

        }

public ValidationErrorDTO getValidationErrorDto (FieldError field){
    String message = msgSource.getMessage(field.getDefaultMessage(), null, currentLocale);
    String objectName = field.getObjectName() ;
    String fieldName = field.getField();
    return new ValidationErrorDTO(objectName, fieldName, message);

}

}
