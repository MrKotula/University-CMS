package ua.foxminded.university.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.service.mapper.GroupMapper;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.GroupValidator;

@Service
@Transactional
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupValidator groupValidator;
    private final GroupRepository groupRepository;
    private final StudentAccountService studentAccountService;
    private final GroupMapper groupMapper;

    @Override
    public void register(String groupName) throws ValidationException {
        groupValidator.validateGroupName(groupName);

        groupRepository.save(new Group(groupName));
    }

    @Override
    public void updateGroupName(GroupResponse groupResponse) throws ValidationException {
        groupValidator.validateGroupName(groupResponse.getGroupName());

        groupRepository.updateGroupName(groupResponse.getGroupName(), groupResponse.getGroupId());
    }

    @Override
    public List<GroupResponse> getGroupsWithLessEqualsStudentCount(int studentCount) {
        List<Group> listOfGroups = groupRepository.getGroupsWithLessEqualsStudentCount(studentCount);

        return groupMapper.transformListGroupsToDto(listOfGroups);
    }

    @Override
    public GroupResponse getGroupByUserId(String userId) {
        StudentAccountResponse studentAccount = studentAccountService.findStudentById(userId);

        Group group = groupRepository.findById(studentAccount.getGroupId()).get();

        return groupMapper.transformGroupToDto(group);
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        updateStudentsSeats();

        return groupMapper.transformListGroupsToDto(groupRepository.findAll());
    }

    @Override
    public void updateStudentsSeats() {
        groupRepository.countGroupSeat();
    }

    @Override
    public GroupResponse getGroupById(String groupId) {
        Group group = groupRepository.findById(groupId).get();

        return groupMapper.transformGroupToDto(group);
    }

    @Override
    public void removeGroup(String groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found!"));

        groupValidator.validateStudentsInGroupBeforeRemove(group);

        groupRepository.removeGroup(groupId);
    }

    @Override
    public String getGroupNameByGroupId(String groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found!"));

        return group.getGroupName();
    }
}
