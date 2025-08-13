package pl.doublecodestudio.nexuserp.interfaces.web.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.doublecodestudio.nexuserp.application.order.command.*;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByIndexQuery;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByIndexQueryHandler;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByLocation;
import pl.doublecodestudio.nexuserp.application.order.query.GetOrderByLocationHandler;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderSummaryDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final ProcessPendingOrdersCommandHandler processPendingOrdersCommandHandler;
    private final GetOrderByIndexQueryHandler getOrderByIndexQueryHandler;
    private final UpdateStatusCommandHandler updateStatusCommandHandler;
    private final GetOrderByLocationHandler getOrderByLocationHandler;

    @PostMapping
    public ResponseEntity<List<OrderDto>> create(@RequestBody CreateOrderCommand command) {
        List<OrderDto> orderDto = createOrderCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    @PostMapping("/process-pending")
    public ResponseEntity<List<OrderSummaryDto>> processPendingOrders(@RequestBody ProcessPendingOrdersCommand command) {
        List<OrderSummaryDto> orderDtos = processPendingOrdersCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }

    @GetMapping(params = {"index", "!location"})
    public ResponseEntity<List<OrderDto>> findByIndex(@RequestParam String index, Pageable pageable) {
        GetOrderByIndexQuery query = new GetOrderByIndexQuery(pageable, index);
        List<OrderDto> orderDtos = getOrderByIndexQueryHandler.handle(query);

        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }

    @GetMapping(params = {"!index", "location"})
    public ResponseEntity<List<OrderDto>> findByLocation(@RequestParam String location, Pageable pageable) {
        GetOrderByLocation query = new GetOrderByLocation(location, pageable);
        List<OrderDto> orders = getOrderByLocationHandler.handle(query);

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PutMapping
    public ResponseEntity<List<OrderDto>> updateStatus(
            @RequestBody UpdateStatusCommand command,
            @RequestParam String index,
            @RequestParam String status) {

        log.info("Update status command: {}", command.getStatus());
        List<OrderDto> orderDtos = updateStatusCommandHandler.handle(command, index, status);

        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }
}