package ua.foxminded.university.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.service.dto.request.GroupRequest;
import ua.foxminded.university.service.dto.response.GroupResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    List<GroupResponse> transformListGroupsToDto(List<Group> group);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupName", source = "groupName")
    @Mapping(target = "countStudents", source = "countStudents")
    GroupResponse transformGroupToDto(Group group);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupName", source = "groupName")
    @Mapping(target = "countStudents", source = "countStudents")
    GroupRequest transformGroupToDtoRequest(Group group);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "groupName", source = "groupName")
    @Mapping(target = "countStudents", source = "countStudents")
    Group transformGroupFromDto(GroupResponse group);

    Group transformGroupFromDto(GroupRequest groupRequest);
}
