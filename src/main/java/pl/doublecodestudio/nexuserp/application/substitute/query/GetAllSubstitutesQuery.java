package pl.doublecodestudio.nexuserp.application.substitute.query;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public record GetAllSubstitutesQuery(Pageable pageable) {
}
