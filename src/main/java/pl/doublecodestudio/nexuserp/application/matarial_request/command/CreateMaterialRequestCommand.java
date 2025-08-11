package pl.doublecodestudio.nexuserp.application.matarial_request.command;

import lombok.Value;

import java.time.Instant;

@Value
public class CreateMaterialRequestCommand {
    String batchId;
    String stageId;
    String finalProductId;
    String finalProductName;
    String unitId;
    String status;
    Instant shippingDate;
    Instant deliveryDate;
    Instant releaseDate;
}
