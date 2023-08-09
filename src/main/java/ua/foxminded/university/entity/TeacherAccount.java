package ua.foxminded.university.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue("US_TA")
public class TeacherAccount extends UserAccount {

    @Builder(builderMethodName = "teacherBuilder")
    public TeacherAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
    }
}
