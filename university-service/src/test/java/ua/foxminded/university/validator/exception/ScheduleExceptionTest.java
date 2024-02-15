package ua.foxminded.university.validator.exception;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNotNull;

class ScheduleExceptionTest {

    private ScheduleException scheduleException;

    @Test
    void testScheduleExceptionNoArgumentConstructor() {
        scheduleException = new ScheduleException();

        assertNotNull(scheduleException);
    }
}
