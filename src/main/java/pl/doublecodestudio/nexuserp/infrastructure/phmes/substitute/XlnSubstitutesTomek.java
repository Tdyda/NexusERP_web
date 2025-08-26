package pl.doublecodestudio.nexuserp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Nationalized;


@Getter
@Setter
@Entity
@Immutable
@Table(name = "xln_substitutes_tomek")
public class XlnSubstitutesTomek {
    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "base_material_id", nullable = false, length = 20)
    private String baseMaterialId;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "subst_material_id", nullable = false, length = 20)
    private String substMaterialId;

}