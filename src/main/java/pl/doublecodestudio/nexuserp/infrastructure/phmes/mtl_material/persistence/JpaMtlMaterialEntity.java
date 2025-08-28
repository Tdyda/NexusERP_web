package pl.doublecodestudio.nexuserp.infrastructure.phmes.mtl_material.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "MTL_Material")
public class JpaMtlMaterialEntity {
    @Id
    @Size(max = 20)
    @Nationalized
    @Column(name = "material_id", nullable = false, length = 20)
    private String materialId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "material_desc")
    private String materialDesc;
}