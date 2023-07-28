package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Entity
@Table(name = "students", schema = "schedule")
@AllArgsConstructor
public class StudentAccount extends UserAccount {

    @Column(name = "group_id")
    private String groupId;

    public StudentAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus,
                          Set<Role> roles, String groupId) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
        this.groupId = groupId;
    }

    public StudentAccount(String groupId, String firstName, String lastName, String email, String password,
                          String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
        this.groupId = groupId;
    }

    public StudentAccount(String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
    }
}
