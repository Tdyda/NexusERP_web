package pl.doublecodestudio.nexuserp.application.matarial_request.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.Filter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetMaterialRequestQuery {
    private final Pageable pageable;
    private final List<Filter> filters;
}
