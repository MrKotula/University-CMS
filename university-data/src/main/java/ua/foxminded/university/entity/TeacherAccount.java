package ua.foxminded.university.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, of = {"degree", "phoneNumber", "diplomaStudents"})
@Entity
@DiscriminatorValue("US_TA")
@Builder
public class TeacherAccount extends UserAccount {
    @Column(name="degrees")
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToMany(targetEntity = StudentAccount.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "diploma_students")
    @JoinTable(
            name = "diploma_students", schema = "schedule",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id_student"))
    private List<StudentAccount> diplomaStudents = new ArrayList<>();

    @Builder(builderMethodName = "teacherBuilder")
    public TeacherAccount(String userId, String firstName, String lastName, String email, String password, String passwordCheck,
                          RegistrationStatus registrationStatus, Set<Role> roles, Degree degree, String phoneNumber, List<StudentAccount> diplomaStudents, Integer version) {
        super(userId, firstName, lastName, email, password, passwordCheck, registrationStatus, roles, version);
        this.degree = degree;
        this.phoneNumber = phoneNumber;
        this.diplomaStudents = diplomaStudents;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + "(" + email + ")";
    }
}
