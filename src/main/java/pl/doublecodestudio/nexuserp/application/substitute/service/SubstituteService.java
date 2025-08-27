package pl.doublecodestudio.nexuserp.application.substitute.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;
import pl.doublecodestudio.nexuserp.domain.substitute.port.SubstituteRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubstituteService {
    private final SubstituteRepository repo;

    public List<Substitute> findByBaseMaterialId(String baseMaterialId) {
        return repo.findByBaseMaterialId(baseMaterialId).stream()
                .map(item -> Substitute.create(item.getBaseIndex(), item.getSubstituteIndex()))
                .toList();
    }

    public Map<String, List<String>> getAllSubstitutes(Collection<String> baseMaterialIds) {
        List<Substitute> substitutes = repo.findByBaseMaterialIdIn(baseMaterialIds);

        return substitutes.stream()
                .collect(Collectors.groupingBy(
                        Substitute::getBaseIndex,
                        Collectors.mapping(Substitute::getSubstituteIndex, Collectors.toList())
                ));
    }
}
