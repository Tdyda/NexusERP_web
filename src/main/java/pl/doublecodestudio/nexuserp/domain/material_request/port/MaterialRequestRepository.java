package pl.doublecodestudio.nexuserp.domain.material_request.port;

import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.Filter;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;

import java.util.List;
import java.util.Optional;

public interface MaterialRequestRepository {
    void save(MaterialRequest materialRequest);

    void saveAll(List<MaterialRequest> materialRequests);

    void delete(MaterialRequest materialRequest);

    List<MaterialRequest> findAll();

//    PageResult<MaterialRequest> findAll(Pageable pageable);

    PageResult<MaterialRequest> findAll(Pageable pageable, List<Filter> filters);

    Optional<MaterialRequest> findById(String id);
}
