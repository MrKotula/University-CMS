package ua.foxminded.university.service.dto.dataupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAccountUpdateRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String passwordCheck;

    private Set<Role> roles;
    private RegistrationStatus registrationStatus;
    private String groupId;
    private String studentCard;
    private Set<Course> courses = new HashSet<>();
}
