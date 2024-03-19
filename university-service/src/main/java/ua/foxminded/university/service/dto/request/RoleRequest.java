package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.enums.RoleModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    private String roleId;
    private RoleModel role;
    private Integer version;

    @Override
    public String toString() {
        return role.toString();
    }
}
