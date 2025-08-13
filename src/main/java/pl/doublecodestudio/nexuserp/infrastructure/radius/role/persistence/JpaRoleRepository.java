package pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaRoleRepository extends JpaRepository<JpaRoleEntity, UUID> {
    Optional<JpaRoleEntity> findByName(String name);
}
