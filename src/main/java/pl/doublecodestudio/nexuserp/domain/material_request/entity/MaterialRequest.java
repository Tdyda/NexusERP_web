package pl.doublecodestudio.nexuserp.domain.material_request.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialRequest {

    private final String batchId;
    private final String stageId;
    private final String finalProductId;
    private final String finalProductName;
    private final String unitId;
    private final String status;
    private final Instant shippingDate;
    private final Instant deliveryDate;
    private final Instant releaseDate;
    private final List<MaterialRequestItem> items;

    private MaterialRequest() {
        this.batchId = null;
        this.stageId = null;
        this.finalProductId = null;
        this.finalProductName = null;
        this.unitId = null;
        this.status = null;
        this.shippingDate = null;
        this.deliveryDate = null;
        this.releaseDate = null;
        this.items = null;
    }

    public static MaterialRequest create(
            String batchId,
            String stageId,
            String finalProductId,
            String finalProductName,
            String unitId,
            String status,
            Instant shippingDate,
            Instant deliveryDate,
            Instant releaseDate,
            List<MaterialRequestItem> items
    ) {
        if (batchId == null || batchId.isEmpty()) {
            throw new IllegalArgumentException("batchId cannot be null or empty");
        }
        if (stageId == null || stageId.isEmpty()) {
            throw new IllegalArgumentException("stageId cannot be null or empty");
        }
        if (finalProductId == null || finalProductId.isEmpty()) {
            throw new IllegalArgumentException("finalProductId cannot be null or empty");
        }
        if (finalProductName == null || finalProductName.isEmpty()) {
            throw new IllegalArgumentException("finalProductName cannot be null or empty");
        }
        if (unitId == null || unitId.isEmpty()) {
            throw new IllegalArgumentException("unitId cannot be null or empty");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("status cannot be null or empty");
        }
        if (shippingDate == null) {
            throw new IllegalArgumentException("shippingDate cannot be null or empty");
        }
        if (deliveryDate == null) {
            throw new IllegalArgumentException("deliveryDate cannot be null or empty");
        }
        if (releaseDate == null) {
            throw new IllegalArgumentException("releaseDate cannot be null or empty");
        }
        if (items == null) {
            throw new IllegalArgumentException("items cannot be null");
        }

        return MaterialRequest.builder()
                .batchId(batchId)
                .stageId(stageId)
                .finalProductId(finalProductId)
                .finalProductName(finalProductName)
                .unitId(unitId)
                .status(status)
                .shippingDate(shippingDate)
                .deliveryDate(deliveryDate)
                .releaseDate(releaseDate)
                .items(items)
                .build();
    }
}
