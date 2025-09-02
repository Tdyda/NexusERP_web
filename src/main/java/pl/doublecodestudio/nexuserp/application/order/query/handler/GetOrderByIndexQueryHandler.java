package pl.doublecodestudio.nexuserp.application.order.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByIndexQuery;
import pl.doublecodestudio.nexuserp.domain.order.port.OrderRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.mapper.OrderMapperDto;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetOrderByIndexQueryHandler {
    private final OrderRepository orderRepository;
    private final OrderMapperDto mapper;

    public List<OrderDto> handle(GetOrderByIndexQuery query) {
        List<OrderDto> list = orderRepository.findByIndex(query.index()).stream()
                .filter(item -> !Objects.equals(item.getStatus(), "Ukończone"))
                .map(mapper::toDto)
                .toList();
        if (list.isEmpty()) {
            log.info("LIST IS EMPTY!");
        }
        list.forEach(item -> log.info(item.toString()));

        return list;
    }
}