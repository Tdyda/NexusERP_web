package pl.doublecodestudio.nexuserp.interfaces.web.material_request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestQuery;
import pl.doublecodestudio.nexuserp.application.matarial_request.query.GetMaterialRequestQueryHandler;
import pl.doublecodestudio.nexuserp.application.matarial_request.service.MaterialRequestSyncService;
import pl.doublecodestudio.nexuserp.domain.material_request.entity.MaterialRequest;

import java.util.List;


@RestController
@RequestMapping("/api/material-requests")
@RequiredArgsConstructor
@Slf4j
public class MaterialRequestController {
    private final MaterialRequestSyncService materialRequestSyncService;
    private final GetMaterialRequestQueryHandler getMaterialRequestQueryHandler;

    @GetMapping
    public ResponseEntity<List<MaterialRequest>> getAll(Pageable pageable) {
        GetMaterialRequestQuery query = new GetMaterialRequestQuery(pageable);
        List<MaterialRequest> result = getMaterialRequestQueryHandler.handle(query);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncNow() {
        materialRequestSyncService.syncMissingMaterialRequests();
        return ResponseEntity.accepted().build();
    }
}
