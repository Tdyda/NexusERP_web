package pl.doublecodestudio.nexuserp.application.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.application.user.command.ChangeUserPasswordCommand;
import pl.doublecodestudio.nexuserp.domain.location.entity.Location;
import pl.doublecodestudio.nexuserp.domain.location.port.LocationRepository;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.role.port.RoleRepository;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.domain.user.port.UserRepository;
import pl.doublecodestudio.nexuserp.exception.InvalidCredentialsException;
import pl.doublecodestudio.nexuserp.exception.UserNotFoundException;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.LoginResponse;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;
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
    private final JwtService jwtService;
    private final LocationRepository locations;

    public User createUser(String email, String password, String userName, String locationCode, Set<String> roles) {
        UUID uuid = UUID.randomUUID();
        String encodedPassword = passwordEncoder.encode(password);
        Set<Role> roleSet = new HashSet<>();

        Location loc = new Location(locationCode);
        if (!locations.existsAndActive(loc)) {
            throw new IllegalArgumentException("Location not active: " + locationCode);
        }

        roles.forEach(role -> {
            roleRepository.findByName(role).ifPresent(roleSet::add);
        });

        User user = User.create(uuid, userName, email, encodedPassword, loc, roleSet);

        userRepository.save(user);
        return user;
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        boolean login = passwordEncoder.matches(password, user.getPassword());

        if (!login) {
            throw new InvalidCredentialsException();
        }

        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        log.info("User: {}", user.getEmail());
        roles.forEach(role -> {
            log.info("Roles: {}", role);
        });

        String refreshToken = TokenGenerator.generateRefreshToken();

        UserDto userDto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles,
                user.getLocation() == null ? null : user.getLocation().code()
        );

        return new LoginResponse(
                jwtService.generateToken(user.getEmail(), roles),
                refreshToken,
                userDto
        );
    }

    public User changePassword(ChangeUserPasswordCommand command) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("Brak uwierzytelnienia");
        }

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(command.oldP(), user.getPassword()))
        {
            throw new InvalidCredentialsException();
        }
        User updated = User.withPassword(user, passwordEncoder.encode(command.newP()));
        userRepository.save(updated);
        log.info("User: {} changed password", user.getUsername());

        return updated;
    }

    private class TokenGenerator {
        private static final SecureRandom secureRandom = new SecureRandom();
        private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

        public static String generateRefreshToken() {
            byte[] randomBytes = new byte[64];
            secureRandom.nextBytes(randomBytes);
            return base64Encoder.encodeToString(randomBytes);
        }
    }
}
