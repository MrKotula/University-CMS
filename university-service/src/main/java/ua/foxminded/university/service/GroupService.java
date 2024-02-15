package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.service.dto.request.GroupRequest;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.validator.exception.ValidationException;

public interface GroupService {
    void register(String groupName) throws ValidationException;

    void updateGroupName(GroupRequest groupRequest) throws ValidationException;

    List<GroupResponse> getGroupsWithLessEqualsStudentCount(int studentCount);

    GroupResponse getGroupByUserId(String userId);

    List<GroupResponse> getAllGroups();

    void updateStudentsSeats();
}
