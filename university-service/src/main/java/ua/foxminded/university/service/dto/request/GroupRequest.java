package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"groupId", "groupName", "version"})
public class GroupRequest {
    private String groupId;
    private String groupName;
    private int countStudents;
    private Integer version;

    @Override
    public String toString() {
        return groupName + ", " + "count of students - " + countStudents;
    }
}
