package pl.doublecodestudio.nexuserp.interfaces.web.order.mapper;

import org.mapstruct.Mapper;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;

@Mapper(componentModel = "spring")
public interface OrderMapperDto {
    OrderDto toDto(Order order);

    Order fromDto(OrderDto orderDto);
}