package pl.doublecodestudio.nexuserp.domain.material_request.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialRequestItem {

    private final String materialId;
    private final String materialName;
    private final String client;
    private final Long amount;

    private MaterialRequestItem() {
        this.materialId = null;
        this.materialName = null;
        this.client = null;
        this.amount = null;
    }

    public static MaterialRequestItem create(String materialId, String materialName, String client, Long amount) {
        if (materialId == null || materialId.isEmpty())
            throw new IllegalArgumentException("materialId cannot be null or empty");
        if (materialName == null || materialName.isEmpty())
            throw new IllegalArgumentException("materialName cannot be null or empty");
        if (client == null || client.isEmpty())
            throw new IllegalArgumentException("client cannot be null or empty");
        if (amount == null || amount < 0)
            throw new IllegalArgumentException("amount cannot be null or negative");

        return MaterialRequestItem.builder()
                .materialId(materialId)
                .materialName(materialName)
                .client(client)
                .amount(amount)
                .build();
    }
}

