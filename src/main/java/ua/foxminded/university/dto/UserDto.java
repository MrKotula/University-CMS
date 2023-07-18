package ua.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.enums.RegistrationStatus;
import ua.foxminded.university.enums.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    @ToString.Exclude private String password;
    @ToString.Exclude private String passwordCheck;
    private Status status;
    private RegistrationStatus registrationStatus;
    
    public UserDto(String firstName, String lastName, String email, String password, String passwordCheck) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
	this.passwordCheck = passwordCheck;
    }
}
