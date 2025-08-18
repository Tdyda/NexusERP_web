package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = MaterialRequestItemMapper.class
)
public interface MaterialRequestPersistenceMapper {

    MaterialRequest toDomain(JpaMaterialRequestEntity entity);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "quantity", source = "quantity")
    JpaMaterialRequestEntity toEntity(MaterialRequest domain);

    default JpaMaterialRequestEntity toEntityWithItems(MaterialRequest domain, @Context MaterialRequestItemMapper itemMapper) {
        JpaMaterialRequestEntity entity = toEntity(domain);

        List<JpaMaterialRequestItemEntity> items = domain.getItems().stream()
                .map(item -> {
                    JpaMaterialRequestItemEntity jpaItem = itemMapper.toEntity(item);
                    jpaItem.setMaterialRequest(entity);
                    return jpaItem;
                })
                .toList();
        entity.setItems(items);
        return entity;
    }
}
