package pl.doublecodestudio.nexuserp.domain.user.entity;

import lombok.Getter;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class User {
    private final UUID id;
    private final String username;
    private final String email;
    private final String password;
    private final Set<Role> roles = new HashSet<>();

    private User(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User create(UUID id, String username, String email, String password, Set<Role> roles) {
        User user = new User(id, username, email, password);
        user.roles.addAll(roles);

        return user;
    }
}
