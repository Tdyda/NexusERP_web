package pl.doublecodestudio.nexuserp.application.order.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByGroupUuidQuery;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrderByGroupUuidQueryHandler {
    private final OrderService orderService;

    public Page<Order> handle(GetOrderByGroupUuidQuery query)
    {
        return orderService.getHistory(query.pageable());
    }
}
