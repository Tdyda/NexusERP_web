package pl.doublecodestudio.nexuserp.interfaces.web.substitute.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;
import pl.doublecodestudio.nexuserp.interfaces.web.substitute.dto.SubstituteDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SubstituteDtoMapper {
    SubstituteDto toDto(Substitute substitute);
}
