package pl.doublecodestudio.nexuserp.application.order.command.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderManualCommand;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;

@Component
@RequiredArgsConstructor
public class CreateOrderManualCommandHandler {
    private final OrderService orderService;

    public OrderDto handle(CreateOrderManualCommand command)
    {
        return orderService.createOrderManual(command);
    }
}