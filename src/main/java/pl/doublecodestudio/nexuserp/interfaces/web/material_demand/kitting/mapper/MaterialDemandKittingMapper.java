package pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKitting;
import pl.doublecodestudio.nexuserp.infrastructure.phmes.material_demand.kitting.persistence.JpaMaterialDemandKittingEntity;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto.MaterialDemandKittingDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MaterialDemandKittingMapper {
    MaterialDemandKittingDto toDto(MaterialDemandKitting materialDemandKitting);

    List<MaterialDemandKittingDto> toDto(List<MaterialDemandKitting> materialDemandKittings);

    MaterialDemandKitting toDomain(JpaMaterialDemandKittingEntity entity);
}
