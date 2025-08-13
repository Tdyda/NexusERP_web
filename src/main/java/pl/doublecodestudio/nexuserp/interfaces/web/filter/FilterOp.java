package pl.doublecodestudio.nexuserp.interfaces.web.filter;

import java.util.Arrays;
import java.util.Optional;

public enum FilterOp {
    EQ("eq"),
    CONTAINS("contains"),
    IN("in"),
    GT("gt"),
    LT("lt"),
    GTE("gte"),
    LTE("lte"),
    BETWEEN("between");

    private final String code;

    FilterOp(String code) {
        this.code = code;
    }

    public static Optional<FilterOp> from(String code) {
        return Arrays.stream(values()).filter(f -> f.code.equalsIgnoreCase(code)).findFirst();
    }
}
