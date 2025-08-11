package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MaterialRequest", schema = "dbo") // <- nazwę tabeli i schematu dopasuj do swojej bazy
@Getter
@Setter
@NoArgsConstructor
public class JpaMaterialRequestEntity {

    @Id
    @Column(name = "BatchId", nullable = false)
    private String batchId;

    @Column(name = "StageId", nullable = false)
    private String stageId;

    @Column(name = "FinalProductId", nullable = false)
    private String finalProductId;

    @Column(name = "FinalProductName", nullable = false)
    private String finalProductName;

    @Column(name = "UnitId", nullable = false)
    private String unitId;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "ShippingDate", nullable = false)
    private Instant shippingDate;

    @Column(name = "DeliveryDate", nullable = false)
    private Instant deliveryDate;

    @Column(name = "ReleaseDate", nullable = false)
    private Instant releaseDate;

    @OneToMany(mappedBy = "materialRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaMaterialRequestItemEntity> items = new ArrayList<>();
}
