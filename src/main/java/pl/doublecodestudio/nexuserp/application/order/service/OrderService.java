package pl.doublecodestudio.nexuserp.application.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderCommand;
import pl.doublecodestudio.nexuserp.application.order.command.ProcessPendingOrdersCommand;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;
import pl.doublecodestudio.nexuserp.domain.order.port.OrderRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.mapper.OrderMapperDto;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final OrderMapperDto mapper;

    public List<OrderDto> createOrder(CreateOrderCommand command) {

        command.getMaterialIds().forEach(id -> {
            log.info("Checking material request with id {}", id);

            if (orderRepository.findByBatchIdAndIndex(command.getBatchId(), id).isPresent()) {
                throw new IllegalArgumentException(
                        "Order with this batchId and materialId already exists!"
                );
            }
        });


        MaterialRequest mr = materialRequestRepository.findById(command.getBatchId()).orElseThrow(
                () -> new IllegalArgumentException("Material request with this batchId doesn't exists!"));

        boolean containsAny = mr.getItems().stream().anyMatch(item ->
                command.getMaterialIds().contains(item.getMaterialId())
        );

        if (!containsAny) {
            throw new IllegalArgumentException("Material request with this batchId and materialIds doesn't exists!");
        }

        List<Order> orders = mr.getItems().stream().filter(item ->
                        command.getMaterialIds().contains(item.getMaterialId())
                ).map(item -> Order.Create(
                        item.getMaterialId(),
                        item.getMaterialName(),
                        item.getAmount().doubleValue(),
                        mr.getUnitId(),
                        Instant.now(),
                        command.getComment(),
                        "Oczekujące",
                        item.getClient(),
                        mr.getBatchId()
                ))
                .toList();

        List<Order> savedOrders = orderRepository.saveAll(orders);

        MaterialRequest updatedMr = mr.withStatus("Closed");
        materialRequestRepository.save(updatedMr);

        return savedOrders.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<OrderDto> updateOrder(ProcessPendingOrdersCommand command) {
        log.info("command status: {}", command.getStatus());

        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, command.getTime());
        Instant instant = localDateTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();

        List<Order> records = orderRepository.findByStatusAndOrderDateLessThanEqual(command.getStatus(), instant);

        records.forEach(order -> {
            log.info("Processing order {}", order.getId());
        });

        List<Order> updatedOrders = new ArrayList<>();

        List<OrderDto> result = records.stream()
                .collect(Collectors.groupingBy(Order::getIndex))
                .entrySet().stream()
                .map(entry -> {
                    String index = entry.getKey();
                    List<Order> ordersWithSameIndex = entry.getValue();

                    List<Order> updatedGroup = ordersWithSameIndex.stream()
                            .map(order -> order.toBuilder()
                                    .status("W trakcie realizacji")
                                    .build())
                            .toList();

                    updatedOrders.addAll(updatedGroup);

                    double totalQuantity = ordersWithSameIndex.stream()
                            .mapToDouble(Order::getQuantity)
                            .sum();

                    OrderDto dto = new OrderDto();

                    dto.setIndex(index);

                    dto.setQuantity(totalQuantity);

                    dto.setStatus("InProgress");

                    dto.setName(ordersWithSameIndex.get(0).getName());

                    dto.setBatchId(ordersWithSameIndex.get(0).getBatchId());
                    dto.setHasComment(ordersWithSameIndex.stream().anyMatch(x -> x.getComment().isEmpty()));
                    dto.setOrderDate(ordersWithSameIndex.stream()
                            .map(Order::getOrderDate)
                            .min(Comparator.naturalOrder())
                            .orElse(null));

                    dto.setHasComment(ordersWithSameIndex.stream().anyMatch(x -> x.getComment() != null &&
                            !x.getComment().isEmpty()));

                    return dto;
                })
                .toList();
        orderRepository.saveAll(updatedOrders);
        return result;
    }

    public List<OrderDto> updateStatus(String index, String oldStatus, String newStatus) {
        List<Order> updatedOrders = orderRepository.findByStatusAndIndex(oldStatus, index).stream()
                .map(order -> order.toBuilder()
                        .status(newStatus).build()
                )
                .toList();

        orderRepository.saveAll(updatedOrders);

        return updatedOrders.stream().map(mapper::toDto).toList();
    }
}
