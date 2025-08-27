package pl.doublecodestudio.nexuserp.infrastructure.phmes.substitute;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface JpaSubstituteRepository extends JpaRepository<XlnSubstitutesTomek, XlnSubstitutesTomekId> {
    List<XlnSubstitutesTomek> findByBaseMaterialId(String baseMaterialId);
    List<XlnSubstitutesTomek> findByBaseMaterialIdIn(Collection<String> baseIndexes);
}
