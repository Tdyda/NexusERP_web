package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import org.mapstruct.Mapper;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequestItem;

@Mapper(componentModel = "spring")
public interface MaterialRequestItemMapper {
    JpaMaterialRequestItemEntity toEntity(MaterialRequestItem item);

    MaterialRequestItem toDomain(JpaMaterialRequestItemEntity entity);
}
