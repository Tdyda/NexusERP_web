package pl.doublecodestudio.nexuserp.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    private UUID groupUUID;
    private String index;
    private Instant orderDate;
    private Double quantity;
    private String name;
    private String prodLine;
}