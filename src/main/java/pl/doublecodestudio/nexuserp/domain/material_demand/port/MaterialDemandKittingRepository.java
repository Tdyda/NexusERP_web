package pl.doublecodestudio.nexuserp.domain.material_demand.port;

import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKitting;

import java.util.List;
import java.util.Optional;

public interface MaterialDemandKittingRepository {
    List<MaterialDemandKitting> findAll();

    List<MaterialDemandKitting> findAllWithPage(Pageable pageable);

    Optional<MaterialDemandKitting> findByBatchId(String batchId);
}
