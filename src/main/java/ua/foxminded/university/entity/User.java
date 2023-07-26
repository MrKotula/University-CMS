package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;

@Data
@EqualsAndHashCode(of= {"userId", "firstName", "lastName", "email"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    protected String userId;
    
    @Column(name = "first_name")
    protected String firstName;
    
    @Column(name = "last_name")
    protected String lastName;
    
    @Column(name = "email")
    protected String email;
    
    @Column(name = "password")
    @ToString.Exclude 
    protected String password;
    
    @Column(name = "password_check")
    @ToString.Exclude 
    protected String passwordCheck;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status")
    private RegistrationStatus registrationStatus;
    
    protected User(String firstName, String lastName, String email, String password, String passwordCheck, Status status, RegistrationStatus registrationStatus) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
	this.passwordCheck = passwordCheck;
	this.status = status;
	this.registrationStatus = registrationStatus;
    }
    
    protected User(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
    }
}
