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
    private final String unitIdHash;
    private final String status;
    private final Instant shippingDate;
    private final Instant deliveryDate;
    private final Instant releaseDate;
    private final List<MaterialRequestItem> items;
    private final String client;
    private final Integer quantity;

    private MaterialRequest() {
        this.batchId = null;
        this.stageId = null;
        this.finalProductId = null;
        this.finalProductName = null;
        this.unitId = null;
        this.unitIdHash = null;
        this.status = null;
        this.shippingDate = null;
        this.deliveryDate = null;
        this.releaseDate = null;
        this.items = null;
        this.client = null;
        this.quantity = null;
    }

    public MaterialRequest withStatus(String newStatus) {
        return MaterialRequest.create(
                this.batchId,
                this.stageId,
                this.finalProductId,
                this.finalProductName,
                this.unitId,
                this.unitIdHash,
                newStatus,
                this.shippingDate,
                this.deliveryDate,
                this.releaseDate,
                this.items,
                this.client,
                this.quantity
        );
    }

    public MaterialRequest withUnitId(String unitId, String unitIdHash) {
        return MaterialRequest.create(
                this.batchId,
                this.stageId,
                this.finalProductId,
                this.finalProductName,
                unitId,
                unitIdHash,
                this.status,
                this.shippingDate,
                this.deliveryDate,
                this.releaseDate,
                this.items,
                this.client,
                this.quantity
        );
    }

    public static MaterialRequest create(
            String batchId,
            String stageId,
            String finalProductId,
            String finalProductName,
            String unitId,
            String unitIdHash,
            String status,
            Instant shippingDate,
            Instant deliveryDate,
            Instant releaseDate,
            List<MaterialRequestItem> items,
            String client,
            Integer quantity
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
        if (client == null || client.isEmpty()) {
            throw new IllegalArgumentException("client cannot be null or empty");
        }
        if(quantity == null){
            throw new IllegalArgumentException("quantity cannot be null or empty");
        }

        return MaterialRequest.builder()
                .batchId(batchId)
                .stageId(stageId)
                .finalProductId(finalProductId)
                .finalProductName(finalProductName)
                .unitId(unitId)
                .unitIdHash(unitIdHash)
                .status(status)
                .shippingDate(shippingDate)
                .deliveryDate(deliveryDate)
                .releaseDate(releaseDate)
                .items(items)
                .client(client)
                .quantity(quantity)
                .build();
    }
}
