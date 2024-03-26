package ua.foxminded.university.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"userId", "firstName", "lastName", "email", "groupId", "studentCard", "version"})
public class StudentAccountResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordCheck;
    private Set<RoleResponse> roles;
    private RegistrationStatus registrationStatus;
    private String groupId;
    private String studentCard;
    private Set<CourseResponse> courses;
    private Integer version;

    @Override
    public String toString() {
        return firstName + lastName + " (" +  email + "), " +
                "groupId=" + groupId + ", " + "studentCard=" + studentCard;
    }
}
