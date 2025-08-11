package pl.doublecodestudio.nexuserp.domain.role.port;

import pl.doublecodestudio.nexuserp.domain.role.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    void save(Role role);

    Optional<Role> findByName(String name);

    List<Role> findAll();
}
