package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.domain.material_request.port.MaterialRequestRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MaterialRequestRepositoryImpl implements MaterialRequestRepository {
    private final JpaMaterialRequestRepository repo;
    private final MaterialRequestPersistenceMapper mapper;
    private final MaterialRequestItemMapper itemMapper;

    @Override
    public void save(MaterialRequest materialRequest) {
        repo.save(mapper.toEntityWithItems(materialRequest, itemMapper));
    }

    @Override
    public void saveAll(List<MaterialRequest> materialRequests) {
        repo.saveAll(materialRequests.stream()
                .map(m -> mapper.toEntityWithItems(m, itemMapper)).collect(Collectors.toList()));
    }

    @Override
    public void delete(MaterialRequest materialRequest) {
        repo.delete(mapper.toEntityWithItems(materialRequest, itemMapper));
    }

    @Override
    public List<MaterialRequest> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MaterialRequest> findAll(Pageable pageable) {
        return repo.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<MaterialRequest> findById(String batchId) {
        return repo.findById(batchId)
                .map(mapper::toDomain);
    }
}
