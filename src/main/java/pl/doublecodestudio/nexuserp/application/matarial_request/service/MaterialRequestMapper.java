package pl.doublecodestudio.nexuserp.application.matarial_request.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKitting;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialRequestMapper {

    @Mapping(target = "finalProductId", source = "sectProdId")
    @Mapping(target = "finalProductName", source = "sectProdDesc")
    @Mapping(target = "status", source = "sectionStatus")
    @Mapping(target = "shippingDate", source = "shippDate")
    @Mapping(target = "client", source = "commentClient")
    MaterialRequest toMaterialRequest(MaterialDemandKitting demand);
}