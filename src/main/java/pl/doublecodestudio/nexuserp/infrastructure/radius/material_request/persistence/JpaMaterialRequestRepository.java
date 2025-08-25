package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface JpaMaterialRequestRepository extends JpaRepository<JpaMaterialRequestEntity, String>, JpaSpecificationExecutor<JpaMaterialRequestEntity> {
    @EntityGraph(attributePaths = "items")
    List<JpaMaterialRequestEntity> findByBatchIdIn(Collection<String> batchIds);
}
