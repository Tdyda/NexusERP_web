package pl.doublecodestudio.nexuserp.infrastructure.radius.location.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLocationRepository extends JpaRepository<JpaLocationEntity, String> {
    boolean existsByCodeAndActiveIsTrue(String code);
}
