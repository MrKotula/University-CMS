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
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.GroupValidator;

@Service
@Transactional
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupValidator groupValidator;
    private final GroupRepository groupRepository;
    private final StudentAccountService studentAccountService;

    @Override
    public void register(String groupName) throws ValidationException {
        groupValidator.validateGroupName(groupName);
        groupRepository.save(new Group(groupName));
    }

    @Override
    public void updateGroupName(Group group) throws ValidationException {
        groupValidator.validateGroupName(group.getGroupName());

        groupRepository.save(group);
    }

    @Override
    public List<Group> getGroupsWithLessEqualsStudentCount(int studentCount) {
        return groupRepository.getGroupsWithLessEqualsStudentCount(studentCount);
    }

    @Override
    public GroupResponse getGroupByUserId(String userId) {
        StudentAccountResponse studentAccount = studentAccountService.findStudentById(userId);

        Group group = groupRepository.findById(studentAccount.getGroupId()).get();

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .countStudents(group.getCountStudents())
                .build();
    }
}
