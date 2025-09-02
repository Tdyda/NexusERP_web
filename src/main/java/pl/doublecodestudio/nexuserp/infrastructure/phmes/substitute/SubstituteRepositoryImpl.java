package pl.doublecodestudio.nexuserp.infrastructure.phmes.substitute;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;
import pl.doublecodestudio.nexuserp.domain.substitute.port.SubstituteRepository;

import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubstituteRepositoryImpl implements SubstituteRepository {
    private final JpaSubstituteRepository repo;
    private final SubstitutePersistenceMapper mapper;

    @Override
    public List<Substitute> findByBaseMaterialId(String baseMaterialId) {
        return repo.findByBaseMaterialId(baseMaterialId).stream()
                .map(item -> Substitute.create(item.getBaseMaterialId(), item.getSubstMaterialId()))
                .toList();
    }

    @Override
    public List<Substitute> findByBaseMaterialIdIn(Collection<String> baseIndexes) {
        return repo.findByBaseMaterialIdIn(baseIndexes).stream()
                .map(item -> Substitute.create(item.getBaseMaterialId(), item.getSubstMaterialId())
                ).toList();
    }

    @Override
    public Page<Substitute> findAll(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toDomain);
    }
}
