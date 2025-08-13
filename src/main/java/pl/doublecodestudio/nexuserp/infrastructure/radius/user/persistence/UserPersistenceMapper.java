package pl.doublecodestudio.nexuserp.infrastructure.radius.user.persistence;

import org.mapstruct.*;
import pl.doublecodestudio.nexuserp.domain.location.entity.Location;
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
//    User toDomain(JpaUserEntity user);

    default User toDomainWithRoles(JpaUserEntity entity, @Context RolePersistenceMapper roleMapper) {
        Set<Role> roleDomain = entity.getRoles().stream()
                .map(roleMapper::toEntity)
                .collect(Collectors.toSet());

        return User.create(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                toLocation(entity.getLocationCode()),
                roleDomain
        );
    }

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "locationRef", ignore = true),
            @Mapping(target = "locationCode", source = "location", qualifiedByName = "locToCode")
    })
    JpaUserEntity toEntity(User domain);

    default JpaUserEntity toEntityWithRoles(User domain, @Context RolePersistenceMapper roleMapper) {
        JpaUserEntity entity = toEntity(domain);
        Set<JpaRoleEntity> roleEntities = domain.getRoles().stream()
                .map(roleMapper::toJpaRole)
                .collect(Collectors.toSet());
        entity.setRoles(roleEntities);
        return entity;
    }

    // --- konwersje pomocnicze ---
    @Named("locToCode")
    default String locToCode(Location loc) { return loc == null ? null : loc.code(); }

    default Location toLocation(String code) { return code == null ? null : new Location(code); }
}