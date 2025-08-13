package pl.doublecodestudio.nexuserp.interfaces.web.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.doublecodestudio.nexuserp.domain.location.entity.Location;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapperDto {

    @Mapping(source = "id", target = "uuid")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "location", target = "locationCode")
    UserDto toDto(User user);

    default Set<String> map(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

     default String map(Location location) {
         return location == null ? null : location.code();
     }
}
