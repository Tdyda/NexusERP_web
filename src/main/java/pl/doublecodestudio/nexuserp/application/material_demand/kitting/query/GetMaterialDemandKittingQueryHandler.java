package pl.doublecodestudio.nexuserp.application.material_demand.kitting.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.material_demand.port.MaterialDemandKittingRepository;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto.MaterialDemandKittingDto;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.mapper.MaterialDemandKittingMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetMaterialDemandKittingQueryHandler {
    private final MaterialDemandKittingRepository materialDemandKittingRepository;
    private final MaterialDemandKittingMapper mapper;

    public List<MaterialDemandKittingDto> handle(GetMaterialDemandKittingQuery query) {
        return materialDemandKittingRepository.findAllWithPage(query.pageable())
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
