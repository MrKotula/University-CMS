package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private DateService dateService;

    @Mock
    private StudentAccountService studentAccountService;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    void shouldThrowEntityNotFoundExceptionWhenEntityNameIsNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        GroupResponse groupResponse = new GroupResponse("", null, 1);

        when(groupService.getGroupByUserId("")).thenReturn(groupResponse);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleToday(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenEntityIsNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        when(groupService.getGroupByUserId("")).thenReturn(null);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleToday(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetListOfScheduleTomorrowReturnNameNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        GroupResponse groupResponse = new GroupResponse("", null, 1);

        when(groupService.getGroupByUserId("")).thenReturn(groupResponse);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleTomorrow(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetListOfScheduleTomorrowNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        when(groupService.getGroupByUserId("")).thenReturn(null);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleTomorrow(""));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
