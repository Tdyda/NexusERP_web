package pl.doublecodestudio.nexuserp.infrastructure.radius.user.persistence;

import org.mapstruct.*;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence.JpaRoleEntity;
import pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence.RolePersistenceMapper;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = RolePersistenceMapper.class)
public interface UserPersistenceMapper {
    User toDomain(JpaUserEntity user);

    default User toDomainWithRoles(JpaUserEntity entity, @Context RolePersistenceMapper roleMapper) {
        Set<Role> roleDomain = entity.getRoles().stream()
                .map(roleMapper::toEntity)
                .collect(Collectors.toSet());

        return User.create(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                roleDomain
        );
    }

    @Mapping(target = "roles", ignore = true)
    JpaUserEntity toEntity(User domain);

    default JpaUserEntity toEntityWithRoles(User domain, @Context RolePersistenceMapper roleMapper) {
        JpaUserEntity entity = toEntity(domain);

        Set<JpaRoleEntity> roleEntities = domain.getRoles().stream()
                .map(roleMapper::toJpaRole)
                .collect(Collectors.toSet());

        entity.setRoles(roleEntities);

        return entity;
    }
}