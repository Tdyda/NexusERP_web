package pl.doublecodestudio.nexuserp.application.substitute.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;
import pl.doublecodestudio.nexuserp.domain.substitute.port.SubstituteRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;
import pl.doublecodestudio.nexuserp.interfaces.web.substitute.dto.SubstituteDto;
import pl.doublecodestudio.nexuserp.interfaces.web.substitute.mapper.SubstituteDtoMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllSubstitutesQueryHandler {
    private final SubstituteRepository substituteRepository;
    private final SubstituteDtoMapper mapper;

    public PageResult<SubstituteDto> handle(GetAllSubstitutesQuery query) {
        Page<Substitute> page = substituteRepository.findAll(query.pageable());
        List<SubstituteDto> dtos = page.getContent().stream()
                .map(mapper::toDto)
                .toList();

        return new PageResult<>(
                dtos,
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }
}
