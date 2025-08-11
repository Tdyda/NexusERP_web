package pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.role.port.RoleRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.role.mapper.RoleMapperDto;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository repo;
    private final RolePersistenceMapper rolePersistenceMapper;
    private final RoleMapperDto roleMapperDto;

    @Override
    public void save(Role role) {
        repo.save(rolePersistenceMapper.toJpaRole(role));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repo.findByName(name)
                .map(rolePersistenceMapper::toEntity);
    }

    @Override
    public List<Role> findAll() {
        return List.of();
    }
}
