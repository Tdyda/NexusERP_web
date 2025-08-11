package pl.doublecodestudio.nexuserp.infrastructure.radius.order.persistence;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderPersistenceMapper {
    JpaOrderEntity toEntity(Order order);

    Order toDomain(JpaOrderEntity entity);
}
