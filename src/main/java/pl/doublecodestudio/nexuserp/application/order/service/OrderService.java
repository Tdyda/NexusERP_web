package pl.doublecodestudio.nexuserp.application.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderCommand;
import pl.doublecodestudio.nexuserp.application.order.command.CreateOrderManualCommand;
import pl.doublecodestudio.nexuserp.application.order.command.ProcessPendingOrdersCommand;
import pl.doublecodestudio.nexuserp.application.substitute.service.SubstituteService;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequestItem;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;
import pl.doublecodestudio.nexuserp.domain.mtl_material.entity.MtlMaterial;
import pl.doublecodestudio.nexuserp.domain.mtl_material.port.MtlMaterialRepository;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;
import pl.doublecodestudio.nexuserp.domain.order.entity.OrderHistory;
import pl.doublecodestudio.nexuserp.domain.order.port.OrderRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.dto.OrderSummaryDto;
import pl.doublecodestudio.nexuserp.interfaces.web.order.mapper.OrderMapperDto;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final MaterialRequestRepository materialRequestRepository;
    private final MtlMaterialRepository mtlMaterialRepository;
    private final SubstituteService substituteService;
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

//        long ordersQuantity = this.countOrdersByLocation(command.getLocationCode());
//        log.info("Ilość zamówień {}", ordersQuantity);
//
//        if (ordersQuantity > 3) {
//            throw new IllegalArgumentException("Zbyt wiele zamówień w statusie oczekującym lub w trakcie realizacji");
//        }


        MaterialRequest mr = materialRequestRepository.findById(command.getBatchId()).orElseThrow(
                () -> new IllegalArgumentException("Material request with this batchId doesn't exists!"));

        Set<String> allowedMaterialIds = new HashSet<>();

        List<String> baseIds = mr.getItems().stream()
                .map(MaterialRequestItem::getMaterialId)
                .toList();

        Map<String, List<String>> substitutesMap = substituteService.getAllSubstitutes(baseIds);

        for (String baseId : baseIds) {
            allowedMaterialIds.add(baseId);
            List<String> substitutes = substitutesMap.getOrDefault(baseId, List.of());
            allowedMaterialIds.addAll(substitutes);
        }


        Map<String, MaterialRequestItem> itemsByBaseId = mr.getItems().stream()
                .collect(Collectors.toMap(MaterialRequestItem::getMaterialId, Function.identity()));

        Map<String, String> substituteToBase = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : substitutesMap.entrySet()) {
            String base = entry.getKey();
            for (String sub : entry.getValue()) {
                substituteToBase.put(sub, base);
            }
        }

        List<Order> orders = command.getMaterialIds().stream()
                .map(selectedId -> {
                    // znajdź bazowy indeks
                    String baseId = itemsByBaseId.containsKey(selectedId)
                            ? selectedId
                            : substituteToBase.get(selectedId);

                    if (baseId == null || !itemsByBaseId.containsKey(baseId)) {
                        throw new IllegalArgumentException("Nieprawidłowy indeks materiału: " + selectedId);
                    }

                    MaterialRequestItem item = itemsByBaseId.get(baseId);

                    return Order.Create(
                            selectedId, // to, co wybrał użytkownik (oryginał lub zamiennik)
                            item.getMaterialName(),
                            item.getAmount().doubleValue(),
                            mr.getUnitId(),
                            Instant.now(),
                            command.getComment(),
                            "Oczekujące",
                            item.getClient(),
                            mr.getBatchId()
                    );
                })
                .toList();


        orders.forEach(item -> log.info("item: {}", item.getIndex()));
        List<Order> savedOrders = orderRepository.saveAll(orders);

        MaterialRequest updatedMr = mr.withStatus("Closed");
        materialRequestRepository.save(updatedMr);

        return savedOrders.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public OrderDto createOrderManual(CreateOrderManualCommand command) {
        MtlMaterial material = mtlMaterialRepository.findById(command.getIndex())
                .orElseThrow(() -> new IllegalArgumentException("Material request with index " + command.getIndex() + " doesn't exists!"));

        long ordersQuantity = this.countOrdersByLocation(command.getLocationCode());
        log.info("Ilość zamówień {}", ordersQuantity);

        if (ordersQuantity > 4) {
            throw new IllegalArgumentException("Zbyt wiele zamówień w statusie oczekującym lub w trakcie realizacji");
        }

        Order order = Order.Create(
                command.getIndex(),
                material.getMaterialDesc(),
                command.getQuantity(),
                command.getLocationCode(),
                Instant.now(),
                command.getComment(),
                "Oczekujące",
                command.getClient(),
                command.getBatchId()
        );

        orderRepository.save(order);

        return mapper.toDto(order);
    }

    public List<OrderSummaryDto> updateOrder(ProcessPendingOrdersCommand command) {
        log.info("command status: {}", command.getStatus());

        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, command.getTime());
        Instant instant = localDateTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();

        List<Order> records = orderRepository.findByStatusAndOrderDateLessThanEqual(command.getStatus(), instant);

        if(records.isEmpty()) {
            log.info("No orders in status Oczekujące found");
        }
        records.forEach(order -> {
            log.info("Processing order {}", order.getId());
        });

        List<Order> updatedOrders = new ArrayList<>();

        UUID uuid = UUID.randomUUID();

        List<OrderDto> result = records.stream()
                .collect(Collectors.groupingBy(Order::getIndex))
                .entrySet().stream()
                .map(entry -> {
                    String index = entry.getKey();
                    List<Order> ordersWithSameIndex = entry.getValue();

                    List<Order> updatedGroup = ordersWithSameIndex.stream()
                            .map(order -> Order.UpdateStatusAndAddUUID(
                                            "W trakcie realizacji",
                                            uuid,
                                            order
                                    )
                            )
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
                    dto.setHasComment(
                            ordersWithSameIndex.stream()
                                    .anyMatch(x -> Optional.ofNullable(x.getComment())
                                            .filter(c -> !c.isEmpty())
                                            .isPresent())
                    );

                    dto.setOrderDate(ordersWithSameIndex.stream()
                            .map(Order::getOrderDate)
                            .min(Comparator.naturalOrder())
                            .orElse(null));

                    dto.setHasComment(ordersWithSameIndex.stream().anyMatch(x -> x.getComment() != null &&
                            !x.getComment().isEmpty()));

                    return dto;
                })
                .collect(Collectors.toList());

        updatedOrders.forEach(order -> {
            log.info("Updating order {}, groupUUID {}", order.getId(), order.getGroupUUID());
        });
        orderRepository.saveAll(updatedOrders);

        return orderRepository
                .findByStatusIn(List.of("W trakcie realizacji", "Przekazane"))
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toMap(
                        OrderDto::getIndex,
                        dto -> OrderSummaryDto.builder()
                                .index(dto.getIndex())
                                .orderDate(dto.getOrderDate())
                                .status(dto.getStatus())
                                .hasComment(dto.isHasComment())
                                .name(dto.getName())
                                .quantity(dto.getQuantity() == null ? 0d : dto.getQuantity())
                                .build(),
                        (a, b) -> {
                            a.setQuantity(a.getQuantity() + b.getQuantity());
                            a.setOrderDate(a.getOrderDate().isBefore(b.getOrderDate()) ? a.getOrderDate() : b.getOrderDate());
                            a.setHasComment(a.isHasComment() || b.isHasComment());
                            return a;
                        }
                ))
                .values()
                .stream()
                .toList();
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

    public List<OrderDto> getAllOrdersByLocationAndStatus(String location) {
        Map<String, OrderDto> byIndex = orderRepository.findByLocation(location).stream()
                .filter(o -> !Objects.equals(o.getStatus(), "Zamknięte"))
                .map(mapper::toDto)
                .collect(Collectors.toMap(
                        OrderDto::getIndex,
                        Function.identity(),
                        (a, b) -> {
                            double aQty = a.getQuantity() != null ? a.getQuantity() : 0.0;
                            double bQty = b.getQuantity() != null ? b.getQuantity() : 0.0;
                            a.setQuantity(aQty + bQty);
                            return a;
                        },
                        LinkedHashMap::new
                ));

        log.info("Quantity of orders: {}", (long) byIndex.size());

        return new ArrayList<>(byIndex.values());
    }

    public Long countOrdersByLocation(String locationCode) {
        Map<String, Map<String, List<Order>>> grouped = orderRepository.findByLocation(locationCode).stream()
                .filter(order -> !order.getStatus().equals("Zamknięte") && !order.getStatus().equals("Ukończone"))
                .collect(Collectors.groupingBy(
                        Order::getIndex,
                        Collectors.groupingBy(Order::getProdLine)
                ));

        return grouped.values().stream()
                .mapToLong(Map::size)
                .sum();
    }

    public PageResult<OrderHistory> getHistory(Pageable pageable) {
        Page<OrderHistory> page = orderRepository.findByGroupUuidNotNullAndStatusIn(pageable, List.of("Zamknięte", "Ukończone"));
        return new PageResult<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

}
