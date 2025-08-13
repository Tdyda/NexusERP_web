package pl.doublecodestudio.nexuserp.interfaces.web.material_request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestByBatchIdQuery;
import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestByBatchIdQueryHandler;
import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestQuery;
import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestQueryHandler;
import pl.doublecodestudio.nexuserp.application.matarial_request.service.MaterialRequestSyncService;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.Filter;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.QueryFilterParser;
import pl.doublecodestudio.nexuserp.interfaces.web.page.PageResult;

import java.util.List;


@RestController
@RequestMapping("/api/material-requests")
@RequiredArgsConstructor
@Slf4j
public class MaterialRequestController {
    private final MaterialRequestSyncService materialRequestSyncService;
    private final GetMaterialRequestQueryHandler getMaterialRequestQueryHandler;
    private final QueryFilterParser queryFilterParser;
    private final GetMaterialRequestByBatchIdQueryHandler getMaterialRequestByBatchIdQueryHandler;

    @GetMapping
    public ResponseEntity<List<MaterialRequest>> getAll(
            Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        List<Filter> filters = queryFilterParser.parse(params);

        PageResult<MaterialRequest> page =
                getMaterialRequestQueryHandler.handle(new GetMaterialRequestQuery(pageable, filters));

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.totalElements()))
                .body(page.content());
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<MaterialRequest> getByBatchId(@PathVariable String batchId) {
        MaterialRequest mr =
                getMaterialRequestByBatchIdQueryHandler.handle(new GetMaterialRequestByBatchIdQuery(batchId));

        return ResponseEntity.ok().body(mr);
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncNow() {
        materialRequestSyncService.syncMissingMaterialRequests();
        return ResponseEntity.accepted().build();
    }
}
