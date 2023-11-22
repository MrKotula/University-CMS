package ua.foxminded.university.service.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String passwordCheck;

    @ToString.Exclude
    private RegistrationStatus registrationStatus;

    @ToString.Exclude
    private Set<Role> roles;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public UserRegistrationRequest(String firstName, String lastName, String email, String password, String passwordCheck) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
