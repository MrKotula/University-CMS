package ua.foxminded.university.validator.exception;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

class ValidationExceptionTest {

    private ValidationException validationException;

    @Test
    void testValidationExceptionNoArgumentConstructor() {
        validationException = new ValidationException();

        assertNotNull(validationException);
    }
}
