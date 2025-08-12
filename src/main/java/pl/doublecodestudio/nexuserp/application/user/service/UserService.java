package pl.doublecodestudio.nexuserp.application.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.role.port.RoleRepository;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.domain.user.port.UserRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.LoginResponse;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;
import pl.doublecodestudio.nexuserp.interfaces.web.user.mapper.UserMapperDto;
import pl.doublecodestudio.nexuserp.security.service.JwtService;

import java.security.SecureRandom;
import java.util.Base64;
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
    private final UserMapperDto mapper;
    private final JwtService jwtService;

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

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        boolean login = passwordEncoder.matches(password, user.getPassword());

        if(!login)
        {
            throw new IllegalArgumentException("Wrong password");
        }

        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        roles.forEach(role -> {log.info("Roles: {}", role);});

        String refreshToken = TokenGenerator.generateRefreshToken();

        UserDto userDto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );

        return new LoginResponse(
                jwtService.generateToken(user.getEmail(), roles),
                refreshToken,
                userDto
        );
    }

    private class TokenGenerator {
        private static final SecureRandom secureRandom = new SecureRandom(); // kryptograficzny RNG
        private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

        public static String generateRefreshToken() {
            byte[] randomBytes = new byte[64]; // 64 bajty = 512 bitów
            secureRandom.nextBytes(randomBytes);
            return base64Encoder.encodeToString(randomBytes);
        }
    }
}
