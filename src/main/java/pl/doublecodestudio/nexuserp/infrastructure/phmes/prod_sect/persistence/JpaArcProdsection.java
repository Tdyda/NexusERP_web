package pl.doublecodestudio.nexuserp.infrastructure.phmes.prod_sect.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ARC_ProdSection")
public class JpaArcProdsection {
    @Id
    @Size(max = 20)
    @NotNull
    @Nationalized
    @Column(name = "order_id", nullable = false, length = 20)
    private String orderId;

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

    @Size(max = 20)
    @Nationalized
    @Column(name = "material_status", length = 20)
    private String materialStatus;

    @Size(max = 20)
    @Nationalized
    @Column(name = "dispense_status", length = 20)
    private String dispenseStatus;

    @Size(max = 20)
    @Nationalized
    @Column(name = "predefined_lot_id", length = 20)
    private String predefinedLotId;

    @Column(name = "scale_factor")
    private Double scaleFactor;

    @Column(name = "sched_begin_time")
    private Instant schedBeginTime;

    @Column(name = "actual_begin_time")
    private Instant actualBeginTime;

    @Column(name = "est_end_time")
    private Instant estEndTime;

    @Column(name = "actual_finish_time")
    private Instant actualFinishTime;

    @Column(name = "actual_close_time")
    private Instant actualCloseTime;

    @Column(name = "sched_end_time")
    private Instant schedEndTime;

    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Nationalized
    @Column(name = "recipe_id", length = 20)
    private String recipeId;

    @Size(max = 20)
    @Nationalized
    @Column(name = "recipe_ver", length = 20)
    private String recipeVer;

    @Size(max = 20)
    @Nationalized
    @Column(name = "pckg_group_name", length = 20)
    private String pckgGroupName;

    @Column(name = "area_id")
    private Integer areaId;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "soitem_id")
    private Integer soitemId;

    @Column(name = "prod_expire_date1")
    private Instant prodExpireDate1;

    @Column(name = "prod_expire_date2")
    private Instant prodExpireDate2;

    @Size(max = 20)
    @Nationalized
    @Column(name = "csb_material_id", length = 20)
    private String csbMaterialId;

    @Column(name = "shipp_date")
    private Instant shippDate;

    @Column(name = "delivery_date")
    private Instant deliveryDate;

    @Column(name = "base_section_id")
    private Integer baseSectionId;

    @Column(name = "base_section_size")
    private Double baseSectionSize;

}