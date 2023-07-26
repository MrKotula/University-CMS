package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Entity
@Table(name = "students", schema = "schedule")
@AllArgsConstructor
@Builder
public class Student extends User {

    @Column(name = "group_id")
    private String groupId;

    public Student(String userId, String firstName, String lastName, String email, String password, String passwordCheck,
                   Status status, RegistrationStatus registrationStatus, String groupId) {
        super(userId, firstName, lastName, email, password, passwordCheck, status, registrationStatus);
        this.groupId = groupId;
    }

    public Student(String groupId, String firstName, String lastName, String email, String password,
                   String passwordCheck, Status status, RegistrationStatus registrationStatus) {
        super(firstName, lastName, email, password, passwordCheck, status, registrationStatus);
        this.groupId = groupId;
    }

    public Student(String firstName, String lastName, String email, String password, String passwordCheck, Status status, RegistrationStatus registrationStatus) {
        super(firstName, lastName, email, password, passwordCheck, status, registrationStatus);
    }
}
