package pl.doublecodestudio.nexuserp.domain.role.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Role {
    private final UUID id;
    private final String name;

    private Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role create(UUID id, String name) {
        return new Role(id, name);
    }
}