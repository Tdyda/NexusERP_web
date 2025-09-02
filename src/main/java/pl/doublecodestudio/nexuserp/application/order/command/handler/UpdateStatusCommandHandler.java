package pl.doublecodestudio.nexuserp.application.order.command.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.command.UpdateStatusCommand;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateStatusCommandHandler {
    private final OrderService orderService;

    public List<OrderDto> handle(UpdateStatusCommand command, String index, String status) {
        return orderService.updateStatus(index, status, command.getStatus());
    }
}
