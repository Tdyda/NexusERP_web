package pl.doublecodestudio.nexuserp.application.matarial_request.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMaterialRequestQueryHandler {
    private final MaterialRequestRepository materialRequestRepository;

    public List<MaterialRequest> handle(GetMaterialRequestQuery query) {
        return materialRequestRepository.findAll(query.pageable());
    }
}
