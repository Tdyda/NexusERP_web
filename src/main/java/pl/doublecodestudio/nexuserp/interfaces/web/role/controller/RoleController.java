package pl.doublecodestudio.nexuserp.interfaces.web.role.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.role.command.CreateRoleCommand;
import pl.doublecodestudio.nexuserp.application.role.command.CreateRoleCommandHandler;
import pl.doublecodestudio.nexuserp.interfaces.web.role.dto.RoleDto;

@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    private final CreateRoleCommandHandler createRoleCommandHandler;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleCommand command) {
        RoleDto role = createRoleCommandHandler.handle(command);
        return ResponseEntity.ok(role);
    }
}