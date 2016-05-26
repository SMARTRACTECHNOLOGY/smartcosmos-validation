package net.smartcosmos.constraint.validation;

import lombok.extern.slf4j.Slf4j;
import net.smartcosmos.constraint.ExclusiveField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FieldListValidator implements ConstraintValidator<ExclusiveField, Object> {

    private String[] fields;

    @Override
    public void initialize(ExclusiveField constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object,
            ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (fields.length > 0) {

            List<String> setFields = definedFields(object);

            if (setFields.size() != 1) {

                if (setFields.isEmpty()) {
                    undefinedFieldsConstraintViolation(constraintValidatorContext);
                }
                else {
                    overspecifiedFieldsConstraintViolations(constraintValidatorContext, setFields);
                }

                return false;
            }
        }

        return true;
    }

    private void overspecifiedFieldsConstraintViolations(
            ConstraintValidatorContext constraintValidatorContext,
            List<String> setFields) {
        String message = String.format("only a single field of %s may be defined", Arrays.toString(fields));

        for (String fieldName : setFields) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }
    }

    private void undefinedFieldsConstraintViolation(
            ConstraintValidatorContext constraintValidatorContext) {
        String message;

        if (fields.length > 1) {
            message = String.format("one of %s may not be empty", Arrays.toString(fields));
        }
        else {
            message = "may not be empty";
        }

        for (String fieldName : fields) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }
    }

    private List<String> definedFields(Object object) {
        List<String> setFields = new ArrayList<>();

        for (String fieldName : fields) {
            try {
                if (!isNullOrEmpty(getValue(object, fieldName))) {
                    setFields.add(fieldName);
                }
            }
            catch (NoSuchFieldException e) {
                log.warn("The field '{}' declared for validation does not exist in class '{}'",
                        fieldName, object.getClass().getName());
            }
            catch (IllegalAccessException e) {
                log.warn("The field '{}' in class '{}' is not accessible", fieldName, object.getClass().getName());
            }
        }

        return setFields;
    }

    private Object getValue(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        return field.get(object);
    }

    private boolean isNullOrEmpty(Object value) {
        return value == null || (value instanceof String && ((String) value).isEmpty());
    }
}
