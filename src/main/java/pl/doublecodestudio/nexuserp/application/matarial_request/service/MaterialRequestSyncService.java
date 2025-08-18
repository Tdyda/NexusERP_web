package pl.doublecodestudio.nexuserp.application.matarial_request.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKitting;
import pl.doublecodestudio.nexuserp.domain.material_demand.port.MaterialDemandKittingRepository;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequestItem;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialRequestSyncService {
    private final MaterialRequestRepository materialRequestRepository;
    private final MaterialDemandKittingRepository materialDemandKittingRepository;
    private final MaterialRequestMapper materialRequestMapper;

    @Transactional
    public void syncMissingMaterialRequests() {
        List<MaterialDemandKitting> recentDemands = getRecentMaterialDemands();
        Set<String> existingBatchIds = getExistingBatchIds();
        List<MaterialRequest> newRequests = generateNewRequests(recentDemands, existingBatchIds);

        if (!newRequests.isEmpty()) {
            materialRequestRepository.saveAll(newRequests);
        }
    }

    private List<MaterialDemandKitting> getRecentMaterialDemands() {
        Instant now = Instant.now();
        Instant from = now.minus(Duration.ofDays(14));
        Instant to = now.plus(Duration.ofDays(14));

        return materialDemandKittingRepository.findAll().stream()
                .filter(item -> {
                    Instant date = item.getReleaseDate();
                    return date.isAfter(from) && date.isBefore(to);
                })
                .toList();
    }

    private Set<String> getExistingBatchIds() {
        return materialRequestRepository.findAll().stream()
                .map(MaterialRequest::getBatchId)
                .collect(Collectors.toSet());
    }

    private MaterialRequest buildMaterialRequestWithItems(List<MaterialDemandKitting> demands) {
        var header = demands.get(0);

        MaterialRequest baseRequest = materialRequestMapper.toMaterialRequest(header);

        List<MaterialRequestItem> items = demands.stream()
                .map(d -> MaterialRequestItem.create(
                        d.getMaterialId(),
                        d.getMaterialDesc(),
                        d.getCommentClient(),
                        d.getIlosc() != null ? d.getIlosc().longValue() : 0L
                ))
                .toList();

        return MaterialRequest.create(
                baseRequest.getBatchId(),
                baseRequest.getStageId(),
                baseRequest.getFinalProductId(),
                baseRequest.getFinalProductName(),
                baseRequest.getUnitId(),
                "New",
                baseRequest.getShippingDate(),
                baseRequest.getDeliveryDate(),
                baseRequest.getReleaseDate(),
                items,
                baseRequest.getClient(),
                baseRequest.getQuantity()
        );
    }

    private List<MaterialRequest> generateNewRequests(List<MaterialDemandKitting> demands, Set<String> existingBatchIds) {
        var groupedDemands = demands.stream()
                .collect(Collectors.groupingBy(MaterialDemandKitting::getBatchId));

        List<MaterialRequest> newList = groupedDemands.entrySet().stream()
                .filter(entry -> !existingBatchIds.contains(entry.getKey()))
                .map(entry -> buildMaterialRequestWithItems(entry.getValue()))
                .toList();

        newList.forEach(item -> {
            log.info("Index: {} , Quantity: {}", item.getFinalProductId(), item.getQuantity());
        });

        return newList;


//        return demands.stream()
//                .collect(Collectors.groupingBy(MaterialDemandKitting::getBatchId)).entrySet().stream()
//                .filter(entry -> !existingBatchIds.contains(entry.getKey()))
//                .map(entry -> buildMaterialRequestWithItems(entry.getValue()))
//                .toList();
    }
}
