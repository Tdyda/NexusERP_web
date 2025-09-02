package pl.doublecodestudio.nexuserp.interfaces.web.filter;

import java.util.List;

/**
 * @param field     np. "batchId"
 * @param op        np. CONTAINS
 * @param rawValues wartości w formie stringów z URL-a
 */

public record Filter(String field, FilterOp op, List<String> rawValues) {
}

