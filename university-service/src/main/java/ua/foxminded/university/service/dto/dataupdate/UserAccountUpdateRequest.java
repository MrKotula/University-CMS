package ua.foxminded.university.service.dto.dataupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"userId", "firstName", "lastName", "email", "roles"})
public class UserAccountUpdateRequest {
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

    public UserAccountUpdateRequest(String userId, String firstName, String lastName, String email, String password, String passwordCheck) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }

    public UserAccountUpdateRequest(String userId, String email, Set<Role> roles) {
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
