package pl.doublecodestudio.nexuserp.application.order.command;

import lombok.Getter;

@Getter
public class CreateOrderManualCommand {
    private String index;
    private String comment;
    private Double quantity;
    private String locationCode;
    private String client;
    private String batchId;
}
