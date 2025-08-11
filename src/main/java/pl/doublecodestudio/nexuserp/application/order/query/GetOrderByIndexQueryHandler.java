package pl.doublecodestudio.nexuserp.application.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.order.port.OrderRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.mapper.OrderMapperDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrderByIndexQueryHandler {
    private final OrderRepository orderRepository;
    private final OrderMapperDto mapper;

    public List<OrderDto> handle(GetOrderByIndexQuery query) {
        return orderRepository.findByIndex(query.index(), query.pageable()).stream()
                .map(mapper::toDto)
                .toList();
    }
}