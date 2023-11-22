package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import ua.foxminded.university.service.DateService;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DateServiceImplTest {
    private static final int NUMBER_OF_TWO_WEEK = 14;

    private DateServiceImpl dateService;

    @Test
    void shouldGetNearNextTwoWeeksTest() {
        DateService dateServiceMocked = spy(DateServiceImpl.class);

        LocalDate localDateTest = LocalDate.of(2023, 1, 1);

        doReturn(localDateTest).when(dateServiceMocked).getCurrentDate();

        List<LocalDate> result = dateServiceMocked.getNearNextTwoWeeks();

        assertEquals(NUMBER_OF_TWO_WEEK, result.size());
        assertEquals(localDateTest, result.get(0));
        assertEquals(localDateTest.plusDays(1), result.get(1));

        verify(dateServiceMocked, times(NUMBER_OF_TWO_WEEK)).getCurrentDate();
    }

    @Test
    void shouldGetCurrantDate() {
        dateService = new DateServiceImpl();

        LocalDate localDate = LocalDate.now();

        assertEquals(localDate, dateService.getCurrentDate());
    }

    @Test
    void shouldGetNextDayOfMonth() {
        dateService = new DateServiceImpl();

        LocalDate localDate = LocalDate.now().plusDays(1);

        assertEquals(localDate, dateService.getNextDayOfMonth());
    }
}
