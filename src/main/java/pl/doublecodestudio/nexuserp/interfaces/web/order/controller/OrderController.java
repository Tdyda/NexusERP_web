package pl.doublecodestudio.nexuserp.interfaces.web.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderCommand;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderManualCommand;
import pl.doublecodestudio.nexuserp.application.order.command.ProcessPendingOrdersCommand;
import pl.doublecodestudio.nexuserp.application.order.command.UpdateStatusCommand;
import pl.doublecodestudio.nexuserp.application.order.command.handler.CreateOrderCommandHandler;
import pl.doublecodestudio.nexuserp.application.order.command.handler.CreateOrderManualCommandHandler;
import pl.doublecodestudio.nexuserp.application.order.command.handler.ProcessPendingOrdersCommandHandler;
import pl.doublecodestudio.nexuserp.application.order.command.handler.UpdateStatusCommandHandler;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByGroupUuidQuery;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByIndexQuery;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByLocation;
import pl.doublecodestudio.nexuserp.application.order.query.handler.GetOrderByGroupUuidQueryHandler;
import pl.doublecodestudio.nexuserp.application.order.query.handler.GetOrderByIndexQueryHandler;
import pl.doublecodestudio.nexuserp.application.order.query.handler.GetOrderByLocationHandler;
import pl.doublecodestudio.nexuserp.application.order.service.OrderService;
import pl.doublecodestudio.nexuserp.domain.order.entity.OrderHistory;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderSummaryDto;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final ProcessPendingOrdersCommandHandler processPendingOrdersCommandHandler;
    private final CreateOrderManualCommandHandler createOrderManualCommandHandler;
    private final GetOrderByIndexQueryHandler getOrderByIndexQueryHandler;
    private final UpdateStatusCommandHandler updateStatusCommandHandler;
    private final GetOrderByLocationHandler getOrderByLocationHandler;
    private final GetOrderByGroupUuidQueryHandler getOrderByGroupUuidQueryHandler;

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ORDER_REQUESTS') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> create(@RequestBody CreateOrderCommand command) {
        List<OrderDto> orderDto = createOrderCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PostMapping("/process-pending")
    @PreAuthorize("hasRole('ORDERS_MEX') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderSummaryDto>> processPendingOrders(@RequestBody ProcessPendingOrdersCommand command) {
        List<OrderSummaryDto> orderDtos = processPendingOrdersCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }

    @PostMapping("/create-manual")
    @PreAuthorize("hasRole('ORDER_REQUESTS') or hasRole('ADMIN')")
    public ResponseEntity<OrderDto> createManual(@RequestBody CreateOrderManualCommand command) {
        OrderDto orderDto = createOrderManualCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @GetMapping("/count-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> countOrders(@RequestParam String locationCode) {
        orderService.countOrdersByLocation(locationCode);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.countOrdersByLocation(locationCode));
    }

    @GetMapping(params = {"index", "!location"})
    @PreAuthorize("hasRole('ORDER_REQUESTS') or hasRole('ORDERS_MEX') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> findByIndex(@RequestParam String index, Pageable pageable) {
        GetOrderByIndexQuery query = new GetOrderByIndexQuery(pageable, index);
        List<OrderDto> orderDtos = getOrderByIndexQueryHandler.handle(query);

        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }

    @GetMapping(params = {"!index", "location"})
    @PreAuthorize("hasRole('ORDER_REQUESTS') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> findByLocation(@RequestParam String location) {
        GetOrderByLocation query = new GetOrderByLocation(location);
        List<OrderDto> orders = getOrderByLocationHandler.handle(query);

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('ORDERS_MEX') or hasRole('ADMIN')")
    public ResponseEntity<PageResult<OrderHistory>> getHistory(Pageable pageable) {
        GetOrderByGroupUuidQuery query = new GetOrderByGroupUuidQuery(pageable);
        PageResult<OrderHistory> history = getOrderByGroupUuidQueryHandler.handle(query);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    @PutMapping
    @PreAuthorize("hasRole('ORDER_REQUESTS') or hasRole('ORDERS_MEX') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> updateStatus(
            @RequestBody UpdateStatusCommand command,
            @RequestParam String index,
            @RequestParam String status) {

        List<OrderDto> orderDtos = updateStatusCommandHandler.handle(command, index, status);

        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }
}