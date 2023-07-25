package ua.foxminded.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.enums.RegistrationStatus;
import ua.foxminded.university.enums.Status;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true)
@Entity
@Table(name="teachers", schema = "schedule")
public class Teacher extends User {
    
    public Teacher(String firstName, String lastName, String email, String password, String passwordCheck, Status status, RegistrationStatus registrationStatus) {
	super(firstName, lastName, email, password, passwordCheck, status, registrationStatus);	
    }
}
