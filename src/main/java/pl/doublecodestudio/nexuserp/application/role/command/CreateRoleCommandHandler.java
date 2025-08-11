package pl.doublecodestudio.nexuserp.application.role.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.role.service.RoleService;
import pl.doublecodestudio.nexuserp.interfaces.web.role.dto.RoleDto;

@Component
@RequiredArgsConstructor
public class CreateRoleCommandHandler {
    private final RoleService roleService;

    public RoleDto handle(CreateRoleCommand command) {
        return roleService.createRole(command.getName());
    }
}
