package ua.foxminded.university.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"groupId", "groupName"})
public class GroupResponse {
    private String groupId;
    private String groupName;
    private int countStudents;
    private Integer version;

    @Override
    public String toString() {
        return groupName + ", " + "count of students - " + countStudents;
    }
}
