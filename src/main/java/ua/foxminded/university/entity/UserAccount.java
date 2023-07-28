package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "users_accounts", schema = "schedule")
public class UserAccount extends User {
    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status")
    protected RegistrationStatus registrationStatus;

    public UserAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles){
        super(userId, firstName, lastName, email, password, passwordCheck, roles);
        this.registrationStatus = registrationStatus;
    }

    public UserAccount(String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles){
        super(firstName, lastName, email, password, passwordCheck, roles);
        this.registrationStatus = registrationStatus;
    }
}
