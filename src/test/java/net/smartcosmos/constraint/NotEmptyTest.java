package net.smartcosmos.constraint;

import net.smartcosmos.test.AbstractValidationTest;
import net.smartcosmos.test.NotEmptyEntity;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotEmptyTest extends AbstractValidationTest {

    @Test
    public void thatValidationPassesIfFooIsSet() {

        NotEmptyEntity entity = NotEmptyEntity.builder()
                .foo("test")
                .build();

        Set<ConstraintViolation<NotEmptyEntity>> constraintViolations = validator.validate(entity);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyFooFailsValidation() {

        NotEmptyEntity entity = NotEmptyEntity.builder()
                .foo("")
                .build();

        Set<ConstraintViolation<NotEmptyEntity>> constraintViolations = validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
        assertEquals(1, constraintViolations.size());
        assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
        assertEquals("foo", constraintViolations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void thatNullFoFailsValidation() {

        NotEmptyEntity entity = NotEmptyEntity.builder()
                .build();

        Set<ConstraintViolation<NotEmptyEntity>> constraintViolations = validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
        assertEquals(1, constraintViolations.size());
        assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
        assertEquals("foo", constraintViolations.iterator().next().getPropertyPath().toString());
    }
}
