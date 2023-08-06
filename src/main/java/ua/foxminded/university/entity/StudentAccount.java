package ua.foxminded.university.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.HashSet;
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

    @Column(name = "student_card")
    private String studentCard;

    @ManyToMany(targetEntity = Course.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "courses")
    @JoinTable(
            name = "students_courses", schema = "schedule",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

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

    @Builder(builderMethodName = "studentBuilder")
    public StudentAccount(String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
    }

    public StudentAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus,
                          Set<Role> roles, String groupId, String studentCard) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
        this.groupId = groupId;
        this.studentCard = studentCard;
    }
}
