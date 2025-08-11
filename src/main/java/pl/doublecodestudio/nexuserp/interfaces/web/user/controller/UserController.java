package pl.doublecodestudio.nexuserp.interfaces.web.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.user.command.CreateUserCommand;
import pl.doublecodestudio.nexuserp.application.user.command.CreateUserCommandHandler;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserCommandHandler createUserCommandHandler;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserCommand command) {
        UserDto user = createUserCommandHandler.handle(command);
        return ResponseEntity.ok(user);
    }
}
