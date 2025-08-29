package pl.doublecodestudio.nexuserp.infrastructure.radius.order.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, Long> {
    Optional<JpaOrderEntity> findByBatchId(String batchId);

    Optional<JpaOrderEntity> findByBatchIdAndIndex(String batchId, String index);

    List<JpaOrderEntity> findByStatus(String status);

    List<JpaOrderEntity> findByIndex(String index);

    List<JpaOrderEntity> findByStatusAndOrderDateLessThanEqual(String status, Instant orderDate);

    List<JpaOrderEntity> findByStatusAndIndex(String status, String index);

    List<JpaOrderEntity> findByProdLine(String prodLine);
}
