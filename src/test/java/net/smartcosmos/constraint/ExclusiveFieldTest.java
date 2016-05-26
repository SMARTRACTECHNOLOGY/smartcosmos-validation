package net.smartcosmos.constraint;

import net.smartcosmos.test.AbstractValidationTest;
import net.smartcosmos.test.ExclusiveFieldEntity;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExclusiveFieldTest extends AbstractValidationTest {

    @Test
    public void thatValidationPassesIfBarIsSet() {

        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .bar(1)
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatValidationPassesIfFooIsSet() {

        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .foo("test")
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatMissingFooOrBarFailValidation() {
        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatSettingBothFooAndBarFailValidation() {
        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .foo("test")
                .bar(1)
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatNullBarFailsValidation() {

        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .bar(null)
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyFooFailsValidation() {

        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .foo("")
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyFooAndBarFailValidation() {

        ExclusiveFieldEntity entity = ExclusiveFieldEntity.builder()
                .foo("")
                .bar(null)
                .build();

        Set<ConstraintViolation<ExclusiveFieldEntity>> constraintViolations =
                validator.validate(entity);

        assertFalse(constraintViolations.isEmpty());
    }
}
