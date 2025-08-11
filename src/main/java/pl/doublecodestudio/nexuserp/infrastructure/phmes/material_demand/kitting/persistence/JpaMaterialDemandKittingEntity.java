package pl.doublecodestudio.nexuserp.infrastructure.phmes.material_demand.kitting.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Nationalized;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKittingId;

import java.time.Instant;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@IdClass(MaterialDemandKittingId.class)
@Immutable
@Table(name = "XLM_kompletacja_zapotrzebowanie_na_materialy_A", schema = "dbo")

public class JpaMaterialDemandKittingEntity {

    @Id
    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "batch_id", nullable = false, length = 20)
    private String batchId;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "stage_id", nullable = false, length = 20)
    private String stageId;

    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "section_id", nullable = false, length = 20)
    private String sectionId;

    @Size(max = 20)
    @Nationalized
    @Column(name = "sect_prod_id", length = 20)
    private String sectProdId;

    @Size(max = 60)
    @Nationalized
    @Column(name = "sect_prod_desc", length = 60)
    private String sectProdDesc;

    @Column(name = "sect_prod_qty")
    private Double sectProdQty;

    @Size(max = 10)
    @Nationalized
    @Column(name = "sect_prod_uom", length = 10)
    private String sectProdUom;

    @Size(max = 40)
    @Nationalized
    @Column(name = "unit_id", length = 40)
    private String unitId;

    @Size(max = 20)
    @Nationalized
    @Column(name = "section_status", length = 20)
    private String sectionStatus;

    @Column(name = "sched_begin_time")
    private Instant schedBeginTime;

    @Size(max = 20)
    @Nationalized
    @Column(name = "recipe_ver", length = 20)
    private String recipeVer;

    @Size(max = 20)
    @Nationalized
    @Column(name = "bom_ver", length = 20)
    private String bomVer;

    @Column(name = "shipp_date")
    private Instant shippDate;

    @Column(name = "delivery_date")
    private Instant deliveryDate;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "release_date")
    private Instant releaseDate;

    @Size(max = 20)
    @Nationalized
    @Column(name = "material_id", length = 20)
    private String materialId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "material_desc")
    private String materialDesc;

    @Column(name = "material_qty")
    private Double materialQty;

    @Size(max = 10)
    @Nationalized
    @Column(name = "material_uom", length = 10)
    private String materialUom;

    @Size(max = 255)
    @Nationalized
    @Column(name = "comment_client")
    private String commentClient;

    @Column(name = "ilosc")
    private Double ilosc;

}