package pl.doublecodestudio.nexuserp.application.matarial_request.command;

import java.time.Instant;

public record CreateMaterialRequestCommand(String batchId, String stageId, String finalProductId,
                                           String finalProductName, String unitId, String status, Instant shippingDate,
                                           Instant deliveryDate, Instant releaseDate) {
}
