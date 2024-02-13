package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"userId", "firstName", "lastName", "email", "groupId", "studentCard"})
public class StudentAccountRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String passwordCheck;

    private Set<RoleRequest> roles;
    private RegistrationStatus registrationStatus;
    private String groupId;
    private String studentCard;
    private Set<CourseRequest> courses;
}
