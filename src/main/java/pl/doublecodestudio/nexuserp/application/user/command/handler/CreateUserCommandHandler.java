package pl.doublecodestudio.nexuserp.application.user.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.user.service.UserService;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;
import pl.doublecodestudio.nexuserp.interfaces.web.user.mapper.UserMapperDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserCommandHandler {
    private final UserService userService;
    private final UserMapperDto userMapperDto;

    public UserDto handle(CreateUserCommand command) {
        UserDto user = userMapperDto.toDto(
                userService.createUser(
                        command.getEmail(),
                        command.getPassword(),
                        command.getUsername(),
                        command.getLocation(),
                        command.getRoles()
                )
        );
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                log.info("Creating user with role {}", role);
            });
        } else {
            log.info("Creating user with empty roles");
        }

        return user;
    }
}
