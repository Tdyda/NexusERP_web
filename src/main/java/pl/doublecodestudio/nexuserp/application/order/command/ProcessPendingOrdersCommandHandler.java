package pl.doublecodestudio.nexuserp.application.order.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessPendingOrdersCommandHandler {
    private final OrderService orderService;

    public List<OrderDto> handle(ProcessPendingOrdersCommand command) {
        return orderService.updateOrder(command);
    }
}
