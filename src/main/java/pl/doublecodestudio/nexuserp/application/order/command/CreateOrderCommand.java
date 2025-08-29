package pl.doublecodestudio.nexuserp.application.order.command;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderCommand {
    String batchId;
    List<String> materialIds;
    String comment;
    String locationCode;
}
