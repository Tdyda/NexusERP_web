package pl.doublecodestudio.nexuserp.domain.mtl_material.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MtlMaterial {
    private String materialId;
    private String materialDesc;

    private MtlMaterial() {
    }

    public static MtlMaterial create(
            String materialId,
            String materialDesc
    ) {
        return MtlMaterial.builder()
                .materialId(materialId)
                .materialDesc(materialDesc)
                .build();
    }
}
