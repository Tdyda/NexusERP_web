package pl.doublecodestudio.nexuserp.infrastructure.radius.user.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.doublecodestudio.nexuserp.infrastructure.radius.location.persistence.JpaLocationEntity;
import pl.doublecodestudio.nexuserp.infrastructure.radius.role.persistence.JpaRoleEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaUserEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String username;
    private String email;
    private String password;

    @Column(name = "location_code", nullable = false, length = 16)
    private String locationCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_code", referencedColumnName = "code",
            insertable = false, updatable = false)
    private JpaLocationEntity locationRef;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<JpaRoleEntity> roles = new HashSet<>();
}
