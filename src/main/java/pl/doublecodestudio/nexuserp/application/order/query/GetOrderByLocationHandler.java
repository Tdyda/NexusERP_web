package pl.doublecodestudio.nexuserp.application.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.domain.order.port.OrderRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.mapper.OrderMapperDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrderByLocationHandler {
    private final OrderService orderService;

    public List<OrderDto> handle(GetOrderByLocation query){
        return orderService.getAllOrdersByLocationAndStatus(query.location());
    }
}
