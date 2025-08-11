package pl.doublecodestudio.nexuserp.application.prod_sect.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.domain.prod_sect.entity.ArcProdsection;
import pl.doublecodestudio.nexuserp.domain.prod_sect.port.ProdSectRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetArcProdSectionQueryHandler {
    private final ProdSectRepository prodSectRepository;

    public List<ArcProdsection> handle(GetArcProdSectionQuery query) {
        return prodSectRepository.findAll(query.pageable());
    }
}
