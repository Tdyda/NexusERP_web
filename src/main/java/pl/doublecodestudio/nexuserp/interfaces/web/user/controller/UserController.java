package pl.doublecodestudio.nexuserp.interfaces.web.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.user.command.ChangeUserPasswordCommand;
import pl.doublecodestudio.nexuserp.application.user.command.CreateUserCommand;
import pl.doublecodestudio.nexuserp.application.user.command.handler.ChangeUserPasswordHandler;
import pl.doublecodestudio.nexuserp.application.user.command.handler.CreateUserCommandHandler;
import pl.doublecodestudio.nexuserp.application.user.command.LoginCommand;
import pl.doublecodestudio.nexuserp.application.user.command.handler.LoginCommandHandler;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.LoginResponse;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserCommandHandler createUserCommandHandler;
    private final LoginCommandHandler loginCommandHandler;
    private final ChangeUserPasswordHandler changeUserPasswordHandler;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserCommand command) {
        UserDto user = createUserCommandHandler.handle(command);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-password")
    public ResponseEntity<UserDto> changePassword(@RequestBody ChangeUserPasswordCommand command) {
        UserDto user = changeUserPasswordHandler.handle(command);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginCommand command) {
        LoginResponse response = loginCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }
}
