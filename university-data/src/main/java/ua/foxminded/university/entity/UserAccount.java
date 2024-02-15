package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("US_AC")
public class UserAccount extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status")
    protected RegistrationStatus registrationStatus;

    @Builder(builderMethodName = "userAccountBuilder")
    public UserAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles, Integer version){
        super(userId, firstName, lastName, email, password, passwordCheck, roles, version);
        this.registrationStatus = registrationStatus;
    }

    public UserAccount(String firstName, String lastName, String email, String password, String passwordCheck){
        super(firstName, lastName, email, password, passwordCheck, new HashSet<>());
    }
}
