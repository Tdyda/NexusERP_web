package pl.doublecodestudio.nexuserp.application.user.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.user.service.UserService;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.LoginResponse;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler {
    private final UserService userService;

    public LoginResponse handle(LoginCommand command) {
        return userService.login(command.email(), command.password());
    }
}
