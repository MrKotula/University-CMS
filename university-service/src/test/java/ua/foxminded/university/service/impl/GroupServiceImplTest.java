package ua.foxminded.university.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.service.mapper.GroupMapper;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupValidator groupValidator;

    @Mock
    private StudentAccountService studentAccountService;

    @Mock
    private GroupMapper groupMapper;

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
        GroupResponse groupResponse = new GroupResponse("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "DT-43", 0,1);

        groupService.updateGroupName(groupResponse);

        verify(groupValidator).validateGroupName("DT-43");
        verify(groupRepository).updateGroupName(groupResponse.getGroupName(), groupResponse.getGroupId());
    }

    @Test
    void shouldReturnListOfGroupsWhenUseGetGroupsWithLessEqualsStudentCount() {
        groupService.getGroupsWithLessEqualsStudentCount(55);

        verify(groupRepository).getGroupsWithLessEqualsStudentCount(55);
    }

    @Test
    void shouldReturnListOfGroupsResponsesWhenUseGetAllGroupsTest() {
        List<Group> listOfGroups = new ArrayList<>();
        List<GroupResponse> listOfGroupsResponses = new ArrayList<>();

        GroupResponse testGroupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        listOfGroups.add(testGroup);
        listOfGroupsResponses.add(testGroupResponse);

        when(groupRepository.findAll()).thenReturn(listOfGroups);
        when(groupMapper.transformListGroupsToDto(listOfGroups)).thenReturn(listOfGroupsResponses);

        assertEquals(listOfGroupsResponses, groupService.getAllGroups());
    }

    @Test
    void verifyMethodUpdateStudentsSeatsTest() {
        groupService.updateStudentsSeats();

        verify(groupRepository).countGroupSeat();
    }

    @Test
    void testGetGroupByUserId() {
        StudentAccountResponse studentAccountResponse = StudentAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("John")
                .lastName("Doe")
                .password("1234")
                .passwordCheck("1234")
                .email("testemail@ukr.net")
                .registrationStatus(RegistrationStatus.NEW)
                .courses(new HashSet<>())
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .build();

        Group group = Group.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        when(studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(studentAccountResponse);
        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.ofNullable(group));
        when(groupMapper.transformGroupToDto(group)).thenReturn(groupResponse);

        assertEquals(groupResponse, groupService.getGroupByUserId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    void shouldReturnGroupResponseWhenUseGetGroupByIdTest() {
        Group group = Group.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(group));
        when(groupMapper.transformGroupToDto(group)).thenReturn(groupResponse);

        groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432");

        verify(groupRepository).findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
        verify(groupMapper).transformGroupToDto(group);
    }

    @Test
    void shouldRemoveGroupWhenUseRemoveGroupTest() {
        Group group = mock(Group.class);

        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(group));
        doNothing().when(groupValidator).validateStudentsInGroupBeforeRemove(group);

        groupService.removeGroup("1d95bc79-a549-4d2c-aeb5-3f929aee5432");

        verify(groupRepository).findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
        verify(groupRepository).removeGroup("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
        verify(groupValidator).validateStudentsInGroupBeforeRemove(group);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUseRemoveGroupTest() {
        String exceptedMessage = "Group not found!";

        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> groupService.removeGroup("1d95bc79-a549-4d2c-aeb5-3f929aee5432"));

        assertEquals(exceptedMessage, exception.getMessage());
        verify(groupRepository).findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
        verify(groupRepository, never()).removeGroup("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
    }
}
