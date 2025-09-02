package pl.doublecodestudio.nexuserp.application.order.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByGroupUuidQuery;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.domain.order.entity.OrderHistory;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;

@Component
@RequiredArgsConstructor
public class GetOrderByGroupUuidQueryHandler {
    private final OrderService orderService;

    public PageResult<OrderHistory> handle(GetOrderByGroupUuidQuery query) {
        return orderService.getHistory(query.pageable());
    }
}
