package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.enums.RoleModel;

@Entity
@Table(name = "roles", schema = "schedule")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private String roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleModel role;

    public Role(RoleModel role) {
        this.role = role;
    }
}
