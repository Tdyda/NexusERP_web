package pl.doublecodestudio.nexuserp.infrastructure.phmes.prod_sect.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.prod_sect.entity.ArcProdsection;
import pl.doublecodestudio.nexuserp.domain.prod_sect.port.ProdSectRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProdSectionRepostioryImpl implements ProdSectRepository {
    private final JpaProdSectionRepository repo;

    @Override
    public List<ArcProdsection> findAll(Pageable pageable) {
        return repo.findAll(pageable).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<ArcProdsection> findById(Integer id) {
        return Optional.empty();
    }

    private ArcProdsection toDomain(JpaArcProdsection entity) {
        return ArcProdsection.builder()
                .orderId(entity.getOrderId())
                .batchId(entity.getBatchId())
                .stageId(entity.getStageId())
                .sectionId(entity.getSectionId())
                .sectProdId(entity.getSectProdId())
                .sectProdDesc(entity.getSectProdDesc())
                .sectProdQty(entity.getSectProdQty())
                .sectProdUom(entity.getSectProdUom())
                .unitId(entity.getUnitId())
                .sectionStatus(entity.getSectionStatus())
                .materialStatus(entity.getMaterialStatus())
                .dispenseStatus(entity.getDispenseStatus())
                .predefinedLotId(entity.getPredefinedLotId())
                .scaleFactor(entity.getScaleFactor())
                .schedBeginTime(entity.getSchedBeginTime())
                .actualBeginTime(entity.getActualBeginTime())
                .estEndTime(entity.getEstEndTime())
                .actualFinishTime(entity.getActualFinishTime())
                .actualCloseTime(entity.getActualCloseTime())
                .schedEndTime(entity.getSchedEndTime())
                .id(entity.getId())
                .recipeId(entity.getRecipeId())
                .recipeVer(entity.getRecipeVer())
                .pckgGroupName(entity.getPckgGroupName())
                .areaId(entity.getAreaId())
                .clientId(entity.getClientId())
                .soitemId(entity.getSoitemId())
                .prodExpireDate1(entity.getProdExpireDate1())
                .prodExpireDate2(entity.getProdExpireDate2())
                .csbMaterialId(entity.getCsbMaterialId())
                .shippDate(entity.getShippDate())
                .deliveryDate(entity.getDeliveryDate())
                .baseSectionId(entity.getBaseSectionId())
                .baseSectionSize(entity.getBaseSectionSize())
                .build();
    }


    private JpaArcProdsection toJpaEntity(ArcProdsection domain) {
        JpaArcProdsection entity = new JpaArcProdsection();

        entity.setOrderId(domain.getOrderId());
        entity.setBatchId(domain.getBatchId());
        entity.setStageId(domain.getStageId());
        entity.setSectionId(domain.getSectionId());
        entity.setSectProdId(domain.getSectProdId());
        entity.setSectProdDesc(domain.getSectProdDesc());
        entity.setSectProdQty(domain.getSectProdQty());
        entity.setSectProdUom(domain.getSectProdUom());
        entity.setUnitId(domain.getUnitId());
        entity.setSectionStatus(domain.getSectionStatus());
        entity.setMaterialStatus(domain.getMaterialStatus());
        entity.setDispenseStatus(domain.getDispenseStatus());
        entity.setPredefinedLotId(domain.getPredefinedLotId());
        entity.setScaleFactor(domain.getScaleFactor());
        entity.setSchedBeginTime(domain.getSchedBeginTime());
        entity.setActualBeginTime(domain.getActualBeginTime());
        entity.setEstEndTime(domain.getEstEndTime());
        entity.setActualFinishTime(domain.getActualFinishTime());
        entity.setActualCloseTime(domain.getActualCloseTime());
        entity.setSchedEndTime(domain.getSchedEndTime());
        entity.setId(domain.getId());
        entity.setRecipeId(domain.getRecipeId());
        entity.setRecipeVer(domain.getRecipeVer());
        entity.setPckgGroupName(domain.getPckgGroupName());
        entity.setAreaId(domain.getAreaId());
        entity.setClientId(domain.getClientId());
        entity.setSoitemId(domain.getSoitemId());
        entity.setProdExpireDate1(domain.getProdExpireDate1());
        entity.setProdExpireDate2(domain.getProdExpireDate2());
        entity.setCsbMaterialId(domain.getCsbMaterialId());
        entity.setShippDate(domain.getShippDate());
        entity.setDeliveryDate(domain.getDeliveryDate());
        entity.setBaseSectionId(domain.getBaseSectionId());
        entity.setBaseSectionSize(domain.getBaseSectionSize());

        return entity;
    }

}
