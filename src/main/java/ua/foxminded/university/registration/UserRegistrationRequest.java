package ua.foxminded.university.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;

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

    private Status status;
    private RegistrationStatus registrationStatus;

    public UserRegistrationRequest(String firstName, String lastName, String email, String password, String passwordCheck) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
