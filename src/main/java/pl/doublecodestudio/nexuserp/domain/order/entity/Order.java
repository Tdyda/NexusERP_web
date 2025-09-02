package pl.doublecodestudio.nexuserp.domain.order.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Order {
    private Integer id;
    private String index;
    private Instant orderDate;
    private String comment;
    private String status;
    private boolean hasComment;
    private String name;
    private Double quantity;
    private String prodLine;
    private String client;
    private String batchId;
    private UUID groupUUID;

    private Order() {
    }

    public static Order Create(
            String index,
            String name,
            Double quantity,
            String prodLine,
            Instant orderDate,
            String comment,
            String status,
            String client,
            String batchId
    ) {
        if (index == null || index.length() != 14) {
            throw new IllegalArgumentException("index length should be 14");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("quantity should not be negative");
        }
        if (prodLine == null || prodLine.isEmpty()) {
            throw new IllegalArgumentException("prodLine should not be empty");
        }
        if (orderDate == null || orderDate.isAfter(Instant.now())) {
            throw new IllegalArgumentException("orderDate should be before now");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("status should not be empty");
        }
        if (client == null || client.isEmpty()) {
            throw new IllegalArgumentException("client should not be empty");
        }
        if (batchId == null || batchId.isEmpty()) {
            throw new IllegalArgumentException("batchId should not be empty");
        }
        boolean hasComment = comment != null && !comment.isEmpty();


        return Order.builder()
                .index(index)
                .name(name)
                .quantity(quantity)
                .prodLine(prodLine)
                .orderDate(orderDate)
                .comment(comment)
                .status(status)
                .hasComment(hasComment)
                .client(client)
                .batchId(batchId)
                .build();
    }


    public static Order UpdateStatusAndAddUUID(String newStatus, UUID newGroupUUID, Order existing) {
        log.info("New group UUID: {}", newGroupUUID);
        if (newGroupUUID == null) {
            throw new IllegalArgumentException("Nieprawidłowe UUID");
        }

        return existing.toBuilder()
                .status(newStatus)
                .groupUUID(newGroupUUID)
                .build();
    }

    public static void UpdateGroupUUID(UUID newGroupUUID, Order existing) {


        existing.groupUUID = newGroupUUID;
    }
}