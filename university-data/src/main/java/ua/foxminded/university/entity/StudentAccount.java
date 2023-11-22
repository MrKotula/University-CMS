package ua.foxminded.university.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.enums.RegistrationStatus;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, of = {"groupId", "studentCard"})
@Entity
@DiscriminatorValue("US_SA")
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

    public StudentAccount(String userId, String groupId, String firstName, String lastName, String email, String password,
                          String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
        this.groupId = groupId;
    }

    @Builder(builderMethodName = "studentBuilder")
    public StudentAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus, Set<Role> roles) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
    }

    public StudentAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck, RegistrationStatus registrationStatus,
                          Set<Role> roles, String groupId, String studentCard) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles);
        this.groupId = groupId;
        this.studentCard = studentCard;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " ( email: " + email + ")";
    }
}
