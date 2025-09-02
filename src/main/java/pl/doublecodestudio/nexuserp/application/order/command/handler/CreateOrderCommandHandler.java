package pl.doublecodestudio.nexuserp.application.order.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderCommand;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderCommandHandler {
    private final OrderService orderService;

    public List<OrderDto> handle(CreateOrderCommand command) {
        List<OrderDto> orders = orderService.createOrder(command);
        orders.forEach(order -> {
            log.info("order: {}", order.toString());
        });

        return orders;
    }
}
