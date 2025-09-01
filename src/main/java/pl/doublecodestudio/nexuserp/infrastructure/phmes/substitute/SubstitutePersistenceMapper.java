package pl.doublecodestudio.nexuserp.infrastructure.phmes.substitute;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SubstitutePersistenceMapper {

    @Mapping(source = "baseIndex", target = "baseMaterialId")
    @Mapping(source = "substituteIndex", target = "substMaterialId")
    XlnSubstitutesTomek toEntity(Substitute domain);

    @Mapping(source = "baseMaterialId", target = "baseIndex")
    @Mapping(source = "substMaterialId", target = "substituteIndex")
    Substitute toDomain(XlnSubstitutesTomek domain);
}
