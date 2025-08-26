package pl.doublecodestudio.nexuserp.infrastructure.phmes.substitute;

import jakarta.persistence.*;
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
@IdClass(XlnSubstitutesTomekId.class)
public class XlnSubstitutesTomek {

    @Id
    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "base_material_id", nullable = false, length = 20)
    private String baseMaterialId;

    @Id
    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "subst_material_id", nullable = false, length = 20)
    private String substMaterialId;

}