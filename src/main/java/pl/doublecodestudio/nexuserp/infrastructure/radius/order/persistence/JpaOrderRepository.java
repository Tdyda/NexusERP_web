package pl.doublecodestudio.nexuserp.infrastructure.radius.order.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, Long> {
    Optional<JpaOrderEntity> findByBatchId(String batchId);

    Optional<JpaOrderEntity> findByBatchIdAndIndex(String batchId, String index);

    List<JpaOrderEntity> findByStatus(String status);

    List<JpaOrderEntity> findByIndex(String index, Pageable pageable);

    List<JpaOrderEntity> findByStatusAndOrderDateLessThanEqual(String status, Instant orderDate);

    List<JpaOrderEntity> findByStatusAndIndex(String status, String index);
}
