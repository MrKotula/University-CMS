package ua.foxminded.university.validator.exception;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

class EntityNotFoundExceptionTest {

    private EntityNotFoundException entityNotFoundException;

    @Test
    void testEntityNotFoundExceptionNoArgumentConstructor() {
        assertNotNull(entityNotFoundException =  new EntityNotFoundException());
    }
}
