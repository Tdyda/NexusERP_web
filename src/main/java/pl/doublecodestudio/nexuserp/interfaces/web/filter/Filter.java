package pl.doublecodestudio.nexuserp.interfaces.web.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Filter {
    private final String field;     // np. "batchId"
    private final FilterOp op;      // np. CONTAINS
    private final List<String> rawValues; // wartości w formie stringów z URL-a
}

