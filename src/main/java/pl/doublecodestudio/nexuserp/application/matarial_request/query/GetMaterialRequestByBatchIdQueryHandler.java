package pl.doublecodestudio.nexuserp.application.matarial_request.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto.MaterialDemandKittingDto;

@Component
@RequiredArgsConstructor
public class GetMaterialRequestByBatchIdQueryHandler {
    private final MaterialRequestRepository materialRequestRepository;

    public MaterialRequest handle(GetMaterialRequestByBatchIdQuery query) {
        return materialRequestRepository.findById(query.batchId()).orElseThrow(IllegalStateException::new);
    }
}
