package pl.doublecodestudio.nexuserp.application.user.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUserCommand {
    private String email;
    private String password;
    private String username;
    private Set<String> roles;
}
