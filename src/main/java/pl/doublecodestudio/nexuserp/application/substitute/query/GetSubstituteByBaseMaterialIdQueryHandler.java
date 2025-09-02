package pl.doublecodestudio.nexuserp.application.substitute.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.substitute.service.SubstituteService;
import pl.doublecodestudio.nexuserp.interfaces.web.substitute.dto.SubstituteDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetSubstituteByBaseMaterialIdQueryHandler {
    private final SubstituteService service;

    public List<SubstituteDto> handle(GetSubstituteByBaseMaterialIdQuery query) {
        return service.findByBaseMaterialId(query.baseMaterialId()).stream()
                .map(item -> new SubstituteDto(item.getBaseIndex(), item.getSubstituteIndex()))
                .toList();
    }
}
