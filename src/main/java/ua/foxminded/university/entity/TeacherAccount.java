package ua.foxminded.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
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
@Table(name = "teachers", schema = "schedule")
public class TeacherAccount extends UserAccount {

    @Builder(builderMethodName = "teacherBuilder")
    public TeacherAccount(String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
    }
}
