package pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MaterialDemandKittingDto {
    private String batchId;
    private String stageId;
    private String sectionId;
    private String sectProdId;
    private String sectProdDesc;
    private Double sectProdQty;
    private String sectProdUom;
    private String unitId;
    private String sectionStatus;
    private Instant schedBeginTime;
    private String recipeVer;
    private String bomVer;
    private Instant shippDate;
    private Instant deliveryDate;
    private Instant createDate;
    private Instant releaseDate;
    private String materialId;
    private String materialDesc;
    private Double materialQty;
    private String materialUom;
    private String commentClient;
    private Double ilosc;
}
