package pl.doublecodestudio.nexuserp.infrastructure.radius.user.persistence;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence.JpaRoleEntity;
import pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence.RolePersistenceMapper;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = RolePersistenceMapper.class)
public interface UserPersistenceMapper {


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