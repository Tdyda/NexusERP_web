package pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.material_demand.kitting.query.GetMaterialDemandKittingByBatchIdQuery;
import pl.doublecodestudio.nexuserp.application.material_demand.kitting.query.GetMaterialDemandKittingByBatchIdQueryHandler;
import pl.doublecodestudio.nexuserp.application.material_demand.kitting.query.GetMaterialDemandKittingQuery;
import pl.doublecodestudio.nexuserp.application.material_demand.kitting.query.GetMaterialDemandKittingQueryHandler;
import pl.doublecodestudio.nexuserp.interfaces.web.material_demand.kitting.dto.MaterialDemandKittingDto;

import java.util.List;

@RestController
@RequestMapping("/api/material-demand-kitting")
@RequiredArgsConstructor
public class MaterialDemandKittingController {
    private final GetMaterialDemandKittingQueryHandler kittingHandler;
    private final GetMaterialDemandKittingByBatchIdQueryHandler kittingByBatchIdHandler;

    @GetMapping
    public ResponseEntity<List<MaterialDemandKittingDto>> getMaterialDemandKitting(Pageable pageable) {
        var query = new GetMaterialDemandKittingQuery(pageable);
        List<MaterialDemandKittingDto> result = kittingHandler.handle(query);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<MaterialDemandKittingDto> getMaterialDemandKitting(@PathVariable String batchId) {
        var query = new GetMaterialDemandKittingByBatchIdQuery(batchId);
        MaterialDemandKittingDto result = kittingByBatchIdHandler.handle(query);

        return ResponseEntity.ok(result);
    }
}
