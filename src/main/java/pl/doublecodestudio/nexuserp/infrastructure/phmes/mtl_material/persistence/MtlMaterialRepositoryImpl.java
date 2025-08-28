package pl.doublecodestudio.nexuserp.infrastructure.phmes.mtl_material.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.mtl_material.entity.MtlMaterial;
import pl.doublecodestudio.nexuserp.domain.mtl_material.port.MtlMaterialRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MtlMaterialRepositoryImpl implements MtlMaterialRepository {
    private final JpaMtlMaterialRepository repo;
    private final MtlMaterialPersistenceMapper mapper;

    @Override
    public Optional<MtlMaterial> findById(String id) {
        return repo.findById(id).map(mapper::toDomain);
    }
}