package pl.doublecodestudio.nexuserp.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import pl.doublecodestudio.nexuserp.domain.location.entity.Location;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class User {
    private final UUID id;
    private final String username;
    private final String email;
    private final String password;
    private final Location location;
    private final Set<Role> roles = new HashSet<>();

    private User(UUID id, String username, String email, String password, Location location) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
    }

    private User(UUID id, String username, String email, String password, Location location, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
        this.roles.addAll(roles);
    }

    public static User create(UUID id, String username, String email, String password, Location location, Set<Role> roles) {
        User user = new User(id, username, email, password, location);
        user.roles.addAll(roles);

        return user;
    }

    public static User withPassword(User existing, String newPassword) {
        return new User(existing.getId(), existing.getUsername(), existing.getEmail(), newPassword, existing.getLocation(), existing.getRoles());
    }
}
