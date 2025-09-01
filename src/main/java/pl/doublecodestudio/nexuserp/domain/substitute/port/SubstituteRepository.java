package pl.doublecodestudio.nexuserp.domain.substitute.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SubstituteRepository {
    List<Substitute> findByBaseMaterialId(String baseMaterialId);
    List<Substitute> findByBaseMaterialIdIn(Collection<String> baseIndexes);
    Page<Substitute> findAll(Pageable pageable);
}
