package pl.doublecodestudio.nexuserp.application.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.role.entity.Role;
import pl.doublecodestudio.nexuserp.domain.role.port.RoleRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.role.dto.RoleDto;
import pl.doublecodestudio.nexuserp.interfaces.web.role.mapper.RoleMapperDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapperDto roleMapperDto;

    public RoleDto createRole(String name) {
        UUID uuid = UUID.randomUUID();
        Role role = Role.create(uuid, name);
        roleRepository.save(role);
        return roleMapperDto.toDto(role);
    }
}
