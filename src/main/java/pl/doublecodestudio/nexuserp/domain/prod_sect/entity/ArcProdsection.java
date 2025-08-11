package pl.doublecodestudio.nexuserp.domain.prod_sect.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ArcProdsection {
    private String orderId;
    private String batchId;
    private String stageId;
    private String sectionId;
    private String sectProdId;
    private String sectProdDesc;
    private Double sectProdQty;
    private String sectProdUom;
    private String unitId;
    private String sectionStatus;
    private String materialStatus;
    private String dispenseStatus;
    private String predefinedLotId;
    private Double scaleFactor;
    private Instant schedBeginTime;
    private Instant actualBeginTime;
    private Instant estEndTime;
    private Instant actualFinishTime;
    private Instant actualCloseTime;
    private Instant schedEndTime;
    private Integer id;
    private String recipeId;
    private String recipeVer;
    private String pckgGroupName;
    private Integer areaId;
    private Integer clientId;
    private Integer soitemId;
    private Instant prodExpireDate1;
    private Instant prodExpireDate2;
    private String csbMaterialId;
    private Instant shippDate;
    private Instant deliveryDate;
    private Integer baseSectionId;
    private Double baseSectionSize;
}
