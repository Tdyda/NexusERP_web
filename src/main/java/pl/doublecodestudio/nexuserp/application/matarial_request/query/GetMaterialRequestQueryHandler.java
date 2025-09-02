package pl.doublecodestudio.nexuserp.application.matarial_request.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;

@Component
@RequiredArgsConstructor
public class GetMaterialRequestQueryHandler {
    private final MaterialRequestRepository repo;

    public PageResult<MaterialRequest> handle(GetMaterialRequestQuery query) {
        return repo.findAll(query.pageable(), query.filters());
    }
}