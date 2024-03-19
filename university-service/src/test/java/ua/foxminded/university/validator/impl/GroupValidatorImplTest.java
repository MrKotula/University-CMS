package ua.foxminded.university.validator.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GroupValidatorImplTest {

    @InjectMocks
    private GroupValidatorImpl groupValidator;

    @Mock
    private GroupRepository groupRepository;

    @Test
    void shouldReturnValidationExceptionWhenGroupNameCannotSpecialCharacter() throws ValidationException {
        String expectedMessage = "Group name cannot special format for group! Use like this format (GR-12)";

        Exception exception = assertThrows(ValidationException.class, () -> groupValidator.validateGroupName("test"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenGroupNameCanSpecialCharacter() throws ValidationException {
        assertDoesNotThrow(() -> groupValidator.validateGroupName("DR-29"));
    }

    @Test
    void shouldReturnValidationExceptionWhenGroupIdIsNotExists() throws ValidationException {
        String expectedMessage = "This groupId is not exists!";

        when(groupRepository.findById("wrong_id")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> groupValidator.validateGroupId("wrong_id"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenGroupIdIsOkExists() throws ValidationException {
        Group testGroup = new Group("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "DT-43");

        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(testGroup));

        assertDoesNotThrow(() -> groupValidator.validateGroupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432"));
        verify(groupRepository).findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
    }

    @Test
    void shouldThrowExceptionWhenValidateStudentsInGroupBeforeRemoveTest() {
        String exceptedMessage = "Students are study in this group!";

        Group group = mock(Group.class);

        when(group.getCountStudents()).thenReturn(1);

        Exception exception = assertThrows(ValidationException.class, () -> groupValidator.validateStudentsInGroupBeforeRemove(group));

        assertEquals(exceptedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNothingWhenValidateStudentsInGroupBeforeRemoveTest() {
        Group group = mock(Group.class);

        when(group.getCountStudents()).thenReturn(0);

        groupValidator.validateStudentsInGroupBeforeRemove(group);
    }
}
