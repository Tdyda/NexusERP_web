package pl.doublecodestudio.nexuserp.application.matarial_request.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKitting;
import pl.doublecodestudio.nexuserp.domain.material_demand.port.MaterialDemandKittingRepository;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequestItem;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
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

        List<MaterialRequest> allCandidates = buildAllCandidates(recentDemands);

        Set<String> batchIds = allCandidates.stream()
                .map(MaterialRequest::getBatchId)
                .collect(Collectors.toSet());

        List<MaterialRequest> existingRequests = materialRequestRepository.findByBatchIdIn(batchIds);
        Map<String, MaterialRequest> existingByBatchId = existingRequests.stream()
                .collect(Collectors.toMap(MaterialRequest::getBatchId, Function.identity()));

        List<MaterialRequest> toInsert = new ArrayList<>();
        List<MaterialRequest> toUpdate = new ArrayList<>();

        for (MaterialRequest candidate : allCandidates) {
            MaterialRequest existing = existingByBatchId.get(candidate.getBatchId());
            if (existing == null) {
                toInsert.add(candidate);
            } else if (!Objects.equals(existing.getUnitIdHash(), candidate.getUnitIdHash())) {
                log.info("Update batch: {}, from unit: {}, to unit: {}", existing.getBatchId(), existing.getUnitId(), candidate.getUnitId());
                MaterialRequest updated = existing.withUnitId(candidate.getUnitId(), candidate.getUnitIdHash());
                toUpdate.add(updated);
            }
        }

        if (!toInsert.isEmpty()) {
            materialRequestRepository.saveAll(toInsert);
        }

        if (!toUpdate.isEmpty()) {
            materialRequestRepository.saveAll(toUpdate);
        }
    }

    private List<MaterialDemandKitting> getRecentMaterialDemands() {
        Instant now = Instant.now();
        Instant from = now.minus(Duration.ofDays(14));
        Instant to = now.plus(Duration.ofDays(14));

        var check = materialDemandKittingRepository.findAll();

        return materialDemandKittingRepository.findAll().stream()
                .filter(item -> item != null && item.getReleaseDate().isAfter(from) && item.getReleaseDate().isBefore(to))
                .toList();
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
        String unitIdHash = hash(baseRequest.getUnitId());

        if (baseRequest.getReleaseDate() == null) {
            log.info("Item null: {}", baseRequest.getBatchId());
        }

        return MaterialRequest.create(
                baseRequest.getBatchId(),
                baseRequest.getStageId(),
                baseRequest.getFinalProductId(),
                baseRequest.getFinalProductName(),
                baseRequest.getUnitId(),
                unitIdHash,
                "New",
                baseRequest.getShippingDate(),
                baseRequest.getDeliveryDate(),
                baseRequest.getReleaseDate(),
                items,
                baseRequest.getClient(),
                baseRequest.getQuantity()
        );
    }

    private String hash(String value) {
        return DigestUtils.sha256Hex(value);
    }

    private List<MaterialRequest> buildAllCandidates(List<MaterialDemandKitting> demands) {
        Map<String, List<MaterialDemandKitting>> groupedDemands = demands.stream()
                .collect(Collectors.groupingBy(MaterialDemandKitting::getBatchId));

        return demands.stream()
                .collect(Collectors.groupingBy(MaterialDemandKitting::getBatchId))
                .values().stream()
                .map(this::buildMaterialRequestWithItems)
                .toList();
    }
}
