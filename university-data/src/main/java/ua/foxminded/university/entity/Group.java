package ua.foxminded.university.entity;

import jakarta.persistence.Version;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups", schema = "schedule")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id")
    @ToString.Exclude
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

    @ColumnDefault(value = "0")
    @ToString.Exclude
    private int countStudents;

    @Version
    @Column(name = "version")
    private Integer version;

    public Group(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }
}
