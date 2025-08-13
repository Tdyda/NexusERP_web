package pl.doublecodestudio.nexuserp.interfaces.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private UUID uuid;
    private String email;
    private String username;
    private Set<String> roles;
    private String locationCode;
}
