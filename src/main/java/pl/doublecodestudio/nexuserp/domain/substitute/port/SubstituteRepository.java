package pl.doublecodestudio.nexuserp.domain.substitute.port;

import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;

import java.util.List;

public interface SubstituteRepository {
    List<Substitute> findByBaseMaterialId(String baseMaterialId);
}
