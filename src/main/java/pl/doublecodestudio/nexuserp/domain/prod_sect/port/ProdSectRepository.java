package pl.doublecodestudio.nexuserp.domain.prod_sect.port;

import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.domain.prod_sect.entity.ArcProdsection;

import java.util.List;
import java.util.Optional;

public interface ProdSectRepository {
    List<ArcProdsection> findAll(Pageable pageable);

    Optional<ArcProdsection> findById(Integer id);
}
