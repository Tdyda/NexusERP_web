package pl.doublecodestudio.nexuserp.interfaces.web.order.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderDto {
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
}
