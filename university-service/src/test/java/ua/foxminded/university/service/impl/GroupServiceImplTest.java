package ua.foxminded.university.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupValidator groupValidator;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group testGroup = new Group("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "DT-43");

    @Test
    void verifyUseMethodRegister() throws ValidationException {
        groupService.register("DT-43");

        verify(groupValidator).validateGroupName("DT-43");
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void verifyUseMethodUpdateGroupName() throws ValidationException {
        groupService.updateGroupName(testGroup);

        verify(groupValidator).validateGroupName("DT-43");
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void shouldReturnListOfGroupsWhenUseGetGroupsWithLessEqualsStudentCount() {
        groupService.getGroupsWithLessEqualsStudentCount(55);

        verify(groupRepository).getGroupsWithLessEqualsStudentCount(55);
    }
}
