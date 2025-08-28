package pl.doublecodestudio.nexuserp.infrastructure.phmes.mtl_material.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.mtl_material.entity.MtlMaterial;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MtlMaterialPersistenceMapper {
    MtlMaterial toDomain(JpaMtlMaterialEntity entity);
    JpaMtlMaterialEntity toEntity(MtlMaterial domain);
}
