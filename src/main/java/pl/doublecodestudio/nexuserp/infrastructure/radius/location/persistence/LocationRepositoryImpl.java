package pl.doublecodestudio.nexuserp.infrastructure.radius.location.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.doublecodestudio.nexuserp.domain.location.entity.Location;
import pl.doublecodestudio.nexuserp.domain.location.port.LocationRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final JpaLocationRepository jpa;

    @Override
    public boolean existsAndActive(Location code) {
        return jpa.existsByCodeAndActiveIsTrue(code.code());
    }

    @Override
    public Optional<LocationInfo> find(Location code) {
        return jpa.findById(code.code())
                .map(e -> new LocationInfo(
                        e.getCode(),
                        e.getLabel(),
                        e.isActive(),
                        e.getSortOrder() == null ? 0 : e.getSortOrder()
                ));
    }
}
