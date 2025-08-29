package pl.doublecodestudio.nexuserp.domain.order.port;

import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    List<Order> saveAll(List<Order> orders);

    List<Order> findByIndex(String batchId);

    Optional<Order> findByBatchIdAndIndex(String id, String index);

    List<Order> findByStatusAndOrderDateLessThanEqual(String status, Instant time);

    List<Order> findByStatus(String status);

    List<Order> findByStatusAndIndex(String status, String index);

    List<Order> findByLocation(String location);
}
