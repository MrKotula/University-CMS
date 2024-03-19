package ua.foxminded.university.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentAccountMapper {

    List<StudentAccountResponse> transformListStudentsToDto(List<StudentAccount> studentAccounts);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "studentCard", source = "studentCard")
    @Mapping(target = "courses", source = "courses")
    @Mapping(target = "version", source = "version")
    StudentAccountResponse transformStudentAccountToDto(StudentAccount studentAccount);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "studentCard", source = "studentCard")
    @Mapping(target = "courses", source = "courses")
    @Mapping(target = "version", source = "version")
    StudentAccount transformStudentAccountFromDto(StudentAccountResponse studentAccountResponse);

    StudentAccount transformStudentAccountFromDto(StudentAccountRequest studentAccountRequest);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "studentCard", source = "studentCard")
    @Mapping(target = "courses", source = "courses")
    @Mapping(target = "version", source = "version")
    StudentAccountRequest transformStudentAccountRequestFromDto(StudentAccountResponse studentAccountResponse);
}
