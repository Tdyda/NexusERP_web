package pl.doublecodestudio.nexuserp.infrastructure.radius.location.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "location_ref")
@Getter
@Setter
@NoArgsConstructor
public class JpaLocationEntity {
    @Id
    @Column(length = 16)
    private String code;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private boolean active = true;

    private Integer sortOrder;
}
