package pl.doublecodestudio.nexuserp.application.matarial_request.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;

@Component
@RequiredArgsConstructor
public class GetMaterialRequestByBatchIdQueryHandler {
    private final MaterialRequestRepository materialRequestRepository;

    public MaterialRequest handle(GetMaterialRequestByBatchIdQuery query) {
        MaterialRequest mr = materialRequestRepository.findById(query.batchId()).orElseThrow(IllegalStateException::new);
        mr.getItems().removeIf(i -> i.getMaterialId().toUpperCase().startsWith("L"));

        return mr;
    }
}
