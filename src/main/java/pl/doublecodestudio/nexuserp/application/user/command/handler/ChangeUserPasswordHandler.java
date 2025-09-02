package pl.doublecodestudio.nexuserp.application.user.command.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.user.command.ChangeUserPasswordCommand;
import pl.doublecodestudio.nexuserp.application.user.service.UserService;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.user.entity.User;
import pl.doublecodestudio.nexuserp.interfaces.web.user.dto.UserDto;
import pl.doublecodestudio.nexuserp.interfaces.web.user.mapper.UserMapperDto;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChangeUserPasswordHandler {
    private final UserService userService;
    private final UserMapperDto userMapperDto;

    public UserDto handle(ChangeUserPasswordCommand command){
        return userMapperDto.toDto(userService.changePassword(command));
//        User user = userService.changePassword(command);
//        Set<String> roles = new HashSet<>();
//        user.getRoles().forEach(role -> roles.add(role.getName()));
//
//        return new UserDto(
//                user.getId(),
//                user.getEmail(),
//                user.getUsername(),
//                roles,
//                user.getLocation().toString()
//        );
    }
}
