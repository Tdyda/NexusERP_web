package pl.doublecodestudio.nexuserp.application.material_demand.kitting.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.material_demand.port.MaterialDemandKittingRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto.MaterialDemandKittingDto;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.mapper.MaterialDemandKittingMapper;

@Component
@RequiredArgsConstructor
public class GetMaterialDemandKittingByBatchIdQueryHandler {
    private final MaterialDemandKittingRepository kittingRepository;
    private final MaterialDemandKittingMapper mapper;

    public MaterialDemandKittingDto handle(GetMaterialDemandKittingByBatchIdQuery query) {
        return kittingRepository.findByBatchId(query.batchId())
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }
}
