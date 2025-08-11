package pl.doublecodestudio.nexuserp.infrastructure.phmes.material_demand.kitting.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.material_demand.entity.MaterialDemandKitting;
import pl.doublecodestudio.nexuserp.domain.material_demand.port.MaterialDemandKittingRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.mapper.MaterialDemandKittingMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MaterialDemandKittingRepositoryImpl implements MaterialDemandKittingRepository {
    private final JpaMaterialDemandKittingRepository repo;
    private final MaterialDemandKittingMapper mapper;

    @Override
    public List<MaterialDemandKitting> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MaterialDemandKitting> findAllWithPage(Pageable pageable) {
        return repo.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<MaterialDemandKitting> findByBatchId(String batchId) {
        return Optional.empty();
    }
}
