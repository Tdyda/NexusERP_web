package pl.doublecodestudio.nexuserp.application.substitute.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;
import pl.doublecodestudio.nexuserp.domain.substitute.port.SubstituteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubstituteService {
    private final SubstituteRepository repo;

    public List<Substitute> findByBaseMaterialId(String baseMaterialId) {
        return repo.findByBaseMaterialId(baseMaterialId).stream()
                .map(item -> Substitute.create(item.getBaseIndex(), item.getSubstituteIndex()))
                .toList();
    }
}
