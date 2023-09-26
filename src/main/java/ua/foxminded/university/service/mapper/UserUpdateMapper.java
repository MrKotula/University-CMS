package ua.foxminded.university.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;

@Mapper
public interface UserUpdateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserAccountFromDto(UserAccountUpdateRequest userAccountUpdateRequest, @MappingTarget UserAccount userAccount);
}
