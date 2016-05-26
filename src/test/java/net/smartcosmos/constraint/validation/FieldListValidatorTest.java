package net.smartcosmos.constraint.validation;

import net.smartcosmos.test.Entity;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldListValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void thatValidationPassesIfIdIsSet() {

        Entity entity = Entity.builder()
                .id("test")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatValidationPassesIfNameIsSet() {

        Entity entity = Entity.builder()
                .name("test")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatMissingIdOrNameFailValidation() {
        Entity entity = Entity.builder()
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatSettingBothIdAndNameFailValidation() {
        Entity entity = Entity.builder()
                .id("test")
                .name("test")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyIdFailsValidation() {

        Entity entity = Entity.builder()
                .id("")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyNameFailsValidation() {

        Entity entity = Entity.builder()
                .name("")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyIdAndNameFailValidation() {

        Entity entity = Entity.builder()
                .id("")
                .name("")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }
}
