package pl.doublecodestudio.nexuserp.domain.substitute.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Substitute {
    private Substitute() {
    }

    private String baseIndex;
    private String substituteIndex;

    public static Substitute create(
            String baseIndex,
            String substituteIndex
    ) {
        if (baseIndex == null || substituteIndex == null)
            throw new NullPointerException("baseIndex or substituteIndex is null");

        if (baseIndex.isEmpty() || substituteIndex.isEmpty())
            throw new NullPointerException("baseIndex or substituteIndex is empty");

        if (baseIndex.length() != 14 || substituteIndex.length() != 14)
            throw new IllegalArgumentException("baseIndex or substituteIndex length should be 14");

        return Substitute.builder()
                .baseIndex(baseIndex)
                .substituteIndex(substituteIndex)
                .build();
    }
}
