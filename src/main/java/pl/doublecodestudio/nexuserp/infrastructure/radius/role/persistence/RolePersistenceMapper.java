package pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.interfaces.web.role.dto.RoleDto;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RolePersistenceMapper {
    JpaRoleEntity toJpaRole(Role role);

    @Named("toDomain")
    default Role toEntity(JpaRoleEntity entity) {
        return Role.create(entity.getId(), entity.getName());
    }

//    @IterableMapping(qualifiedByName = "toDomain")
//    default Set<Role> toEntitySet(Set<JpaRoleEntity> entities) {
//        if (entities == null) return Set.of();
//        return entities.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toSet());
//    }
}