package pl.doublecodestudio.nexuserp.domain.material_request.port;

import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;

import java.util.List;
import java.util.Optional;

public interface MaterialRequestRepository {
    void save(MaterialRequest materialRequest);

    void saveAll(List<MaterialRequest> materialRequests);

    void delete(MaterialRequest materialRequest);

    List<MaterialRequest> findAll();

    List<MaterialRequest> findAll(Pageable pageable);

    Optional<MaterialRequest> findById(String id);
}
