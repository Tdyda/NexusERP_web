package pl.doublecodestudio.nexuserp.domain.location.port;

import pl.doublecodestudio.nexuserp.domain.location.entity.Location;

import java.util.Optional;

public interface LocationRepository {
    boolean existsAndActive(Location code);
    Optional<LocationInfo> find(Location code); // np. code + label + group + sort
    record LocationInfo(String code, String label, boolean active, int sortOrder) {}
}

