package pl.doublecodestudio.nexuserp.interfaces.web.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class OrderSummaryDto {
    private String index;
    private Instant orderDate;
    private String status;
    private boolean hasComment;
    private String name;
    private double quantity; // lub BigDecimal, jeśli wolisz
}