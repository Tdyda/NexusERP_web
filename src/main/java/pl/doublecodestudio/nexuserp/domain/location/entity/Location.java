package pl.doublecodestudio.nexuserp.domain.location.entity;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record Location(String code) {
    public Location {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Location code must not be blank");
        }
    }

    @JsonValue
    public String json() { return code; }

    @JsonCreator
    public static Location of(String code) { return new Location(code); }
}
