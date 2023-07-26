package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.validator.exception.ValidationException;

public interface GroupService {
    void register(String groupName) throws ValidationException;
    
    void updateGroupName(Group group) throws ValidationException;
    
    List<Group> getGroupsWithLessEqualsStudentCount(int studentCount);
}
