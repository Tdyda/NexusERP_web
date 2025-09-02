package pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;

@Mapper(componentModel = "spring")
public interface RolePersistenceMapper {
    JpaRoleEntity toJpaRole(Role role);

    @Named("toDomain")
    default Role toEntity(JpaRoleEntity entity) {
        return Role.create(entity.getId(), entity.getName());
    }
}