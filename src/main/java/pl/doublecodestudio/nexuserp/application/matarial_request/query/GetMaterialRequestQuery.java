package pl.doublecodestudio.nexuserp.application.matarial_request.query;

import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.Filter;

import java.util.List;

public record GetMaterialRequestQuery(Pageable pageable, List<Filter> filters) {
}
