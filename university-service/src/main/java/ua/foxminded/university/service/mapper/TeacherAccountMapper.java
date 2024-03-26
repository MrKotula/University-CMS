package ua.foxminded.university.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.service.dto.request.TeacherAccountRequest;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherAccountMapper {

    List<TeacherAccountResponse> transformListTeachersToDto(List<TeacherAccount> teacherAccounts);

    List<TeacherAccount> transformListTeachersFromDto(List<TeacherAccountResponse> teacherAccounts);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "diplomaStudents", source = "diplomaStudents")
    @Mapping(target = "version", source = "version")
    TeacherAccountResponse transformTeacherAccountToDto(TeacherAccount teacherAccount);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "diplomaStudents", source = "diplomaStudents")
    @Mapping(target = "version", source = "version")
    TeacherAccount transformTeacherAccountFromDto(TeacherAccountResponse teacherAccountResponse);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "degree", source = "degree")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "diplomaStudents", source = "diplomaStudents")
    @Mapping(target = "version", source = "version")
    TeacherAccountRequest transformTeacherAccountToDtoRequest(TeacherAccount teacherAccount);
}
