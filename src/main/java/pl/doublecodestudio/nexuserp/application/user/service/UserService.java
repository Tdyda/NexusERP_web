package pl.doublecodestudio.nexuserp.application.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.role.port.RoleRepository;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.domain.user.port.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String email, String password, String userName, Set<String> roles) {
        UUID uuid = UUID.randomUUID();
        String encodedPassword = passwordEncoder.encode(password);
        Set<Role> roleSet = new HashSet<>();

        roles.forEach(role -> {
            log.info("Roles before mapping: {}", role);
        });

        roles.forEach(role -> {
            roleRepository.findByName(role).ifPresent(roleSet::add);
        });

        User user = User.create(uuid, userName, email, encodedPassword, roleSet);

        userRepository.save(user);
        return user;
    }
}
