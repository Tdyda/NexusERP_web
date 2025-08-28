package pl.doublecodestudio.nexuserp.domain.mtl_material.port;

import pl.doublecodestudio.nexuserp.domain.mtl_material.entity.MtlMaterial;

import java.util.Optional;

public interface MtlMaterialRepository {
    Optional<MtlMaterial> findById(String id);
}
