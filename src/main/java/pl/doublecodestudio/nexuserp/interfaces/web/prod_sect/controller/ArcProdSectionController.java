package pl.doublecodestudio.nexuserp.interfaces.web.prod_sect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.doublecodestudio.nexuserp.application.prod_sect.query.GetArcProdSectionQuery;
import pl.doublecodestudio.nexuserp.application.prod_sect.query.GetArcProdSectionQueryHandler;
import pl.doublecodestudio.nexuserp.domain.prod_sect.entity.ArcProdsection;

import java.util.List;

@RestController
@RequestMapping("/api/arc-prod-section")
@RequiredArgsConstructor
public class ArcProdSectionController {
    private final GetArcProdSectionQueryHandler getArcProdSectionQueryHandler;

    @GetMapping
    public ResponseEntity<List<ArcProdsection>> findAll(Pageable pageable) {
        var query = new GetArcProdSectionQuery(pageable);
        List<ArcProdsection> result = getArcProdSectionQueryHandler.handle(query);

        return ResponseEntity.ok(result);
    }
}