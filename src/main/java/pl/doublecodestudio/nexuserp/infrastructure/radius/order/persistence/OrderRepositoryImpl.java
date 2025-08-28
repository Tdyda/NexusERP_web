package pl.doublecodestudio.nexuserp.infrastructure.radius.order.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.order.entity.Order;
import pl.doublecodestudio.nexuserp.domain.order.port.OrderRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaOrderRepository repo;
    private final OrderPersistenceMapper mapper;

    @Override
    public void save(Order order) {
        repo.save(mapper.toEntity(order));
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        return repo.saveAll(orders.stream()
                        .map(mapper::toEntity)
                        .toList()
                ).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByIndex(String index, Pageable pageable) {
        return repo.findByIndex(index, pageable).stream()
                .filter(item -> !Objects.equals(item.getStatus(), "Zamknięte"))
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Order> findByBatchIdAndIndex(String batchId, String index) {
        return repo.findByBatchIdAndIndex(batchId, index)
                .map(mapper::toDomain);
    }

    @Override
    public List<Order> findByStatusAndOrderDateLessThanEqual(String status, Instant timestamp) {
        return repo.findByStatusAndOrderDateLessThanEqual(status, timestamp)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByStatus(String status) {
        return repo.findByStatus(status).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByStatusAndIndex(String status, String index) {
        return repo.findByStatusAndIndex(status, index).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByLocation(String location) {
        return repo.findByProdLine(location).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
