package pl.doublecodestudio.nexuserp.interfaces.web.substitute.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.material_demand.kitting.query.GetMaterialDemandKittingByBatchIdQuery;
import pl.doublecodestudio.nexuserp.application.substitute.query.GetAllSubstitutesQuery;
import pl.doublecodestudio.nexuserp.application.substitute.query.GetAllSubstitutesQueryHandler;
import pl.doublecodestudio.nexuserp.application.substitute.query.GetSubstituteByBaseMaterialIdQuery;
import pl.doublecodestudio.nexuserp.application.substitute.query.GetSubstituteByBaseMaterialIdQueryHandler;
import pl.doublecodestudio.nexuserp.domain.substitute.entity.Substitute;
import pl.doublecodestudio.nexuserp.infrastructure.phmes.substitute.XlnSubstitutesTomek;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto.MaterialDemandKittingDto;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;
import pl.doublecodestudio.nexuserp.interfaces.web.substitute.dto.SubstituteDto;

import java.util.List;

@RestController
@RequestMapping("/api/substitutes")
@RequiredArgsConstructor
@Slf4j
public class SubstituteController {
    private final GetSubstituteByBaseMaterialIdQueryHandler handler;
    private final GetAllSubstitutesQueryHandler allHandler;

    @GetMapping("/{baseMaterialId}")
    public ResponseEntity<List<SubstituteDto>> findByBaseMaterialId(@PathVariable String baseMaterialId) {
        GetSubstituteByBaseMaterialIdQuery query = new GetSubstituteByBaseMaterialIdQuery(baseMaterialId);
        List<SubstituteDto> substitutes = handler.handle(query);

        return ResponseEntity.ok(substitutes);
    }

    @GetMapping
    public ResponseEntity<PageResult<SubstituteDto>> findAll(Pageable pageable) {
        GetAllSubstitutesQuery query = new GetAllSubstitutesQuery(pageable);
        PageResult<SubstituteDto> substitutes = allHandler.handle(query);

        return ResponseEntity.ok(substitutes);
    }
}