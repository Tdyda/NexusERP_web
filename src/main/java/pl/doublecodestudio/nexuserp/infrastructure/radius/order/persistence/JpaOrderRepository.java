package pl.doublecodestudio.nexuserp.infrastructure.radius.order.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.doublecodestudio.nexuserp.domain.order.entity.OrderHistory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, Long> {
    Optional<JpaOrderEntity> findByBatchId(String batchId);

    Optional<JpaOrderEntity> findByBatchIdAndIndex(String batchId, String index);

    List<JpaOrderEntity> findByStatus(String status);

    List<JpaOrderEntity> findByStatusIn(List<String> statuses);

    List<JpaOrderEntity> findByIndex(String index);

    List<JpaOrderEntity> findByStatusAndOrderDateLessThanEqual(String status, Instant orderDate);

    List<JpaOrderEntity> findByStatusAndIndex(String status, String index);

    List<JpaOrderEntity> findByProdLine(String prodLine);

    @Query(
            value = """
                    select new pl.doublecodestudio.nexuserp.domain.order.entity.OrderHistory(
                        o.groupUUID,
                        o.index,
                        min(o.orderDate),
                        sum(cast(o.quantity as double)),
                        max(o.name),
                        max(o.prodLine)
                    )
                    from JpaOrderEntity o
                    where o.groupUUID is not null
                    group by o.groupUUID, o.index
                    """,
            countQuery = """
                    select count(distinct o.groupUUID, o.index)
                    from JpaOrderEntity o
                    where o.groupUUID is not null
                    """
    )
    Page<OrderHistory> findByGroupUUIDIsNotNull(Pageable pageable);
}
