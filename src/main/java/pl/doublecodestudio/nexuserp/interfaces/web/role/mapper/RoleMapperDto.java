package pl.doublecodestudio.nexuserp.interfaces.web.role.mapper;

import org.mapstruct.Mapper;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.interfaces.web.role.dto.RoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapperDto {
    RoleDto toDto(Role role);
}