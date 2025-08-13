package pl.doublecodestudio.nexuserp.infrastructure.radius.user.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.location.port.LocationRepository;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.domain.user.port.UserRepository;
import pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence.RolePersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository repo;
    private final UserPersistenceMapper mapper;
    private final RolePersistenceMapper roleMapper;
    private final LocationRepository locations;

    @Override
    public void save(User user) {
        if (!locations.existsAndActive(user.getLocation())) {
            throw new IllegalArgumentException("Location not active or unknown: " + user.getLocation().code());
        }
        repo.save(mapper.toEntityWithRoles(user, roleMapper));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email)
                .map(entity -> mapper.toDomainWithRoles(entity, roleMapper));
    }
}
