package pl.doublecodestudio.nexuserp.domain.user.port;

import pl.doublecodestudio.nexuserp.domain.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();
}