package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaMaterialRequestRepository extends JpaRepository<JpaMaterialRequestEntity, String>, JpaSpecificationExecutor<JpaMaterialRequestEntity> {
}
