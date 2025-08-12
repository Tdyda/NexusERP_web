package pl.doublecodestudio.nexuserp.interfaces.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QueryFilterParser {

    private static final Pattern OP_PATTERN = Pattern.compile("^f\\.(?<field>[\\w.]+)\\[op]$");
    private static final Pattern VALUE_PATTERN = Pattern.compile("^f\\.(?<field>[\\w.]+)\\[value]$");
    private static final Pattern VALUES_PATTERN = Pattern.compile("^f\\.(?<field>[\\w.]+)\\[values]$");
    private static final Pattern FROM_PATTERN = Pattern.compile("^f\\.(?<field>[\\w.]+)\\[from]$");
    private static final Pattern TO_PATTERN = Pattern.compile("^f\\.(?<field>[\\w.]+)\\[to]$");

    public List<Filter> parse(MultiValueMap<String, String> params) {
        // grupujemy dane pomocniczo: field -> map atrybutów
        Map<String, Map<String, List<String>>> tmp = new HashMap<>();

        for (String key : params.keySet()) {
            List<String> values = params.get(key);
            if (values == null) continue;

            Matcher m;

            if ((m = OP_PATTERN.matcher(key)).matches()) {
                put(tmp, m.group("field"), "op", values);
            } else if ((m = VALUE_PATTERN.matcher(key)).matches()) {
                put(tmp, m.group("field"), "value", values);
            } else if ((m = VALUES_PATTERN.matcher(key)).matches()) {
                put(tmp, m.group("field"), "values", values);
            } else if ((m = FROM_PATTERN.matcher(key)).matches()) {
                put(tmp, m.group("field"), "from", values);
            } else if ((m = TO_PATTERN.matcher(key)).matches()) {
                put(tmp, m.group("field"), "to", values);
            }
        }

        List<Filter> result = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<String>>> e : tmp.entrySet()) {
            String field = e.getKey();
            Map<String, List<String>> parts = e.getValue();

            String opStr = first(parts.get("op")).orElse("eq");
            FilterOp op = FilterOp.from(opStr)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown op: " + opStr + " for field: " + field));

            List<String> rawValues = switch (op) {
                case BETWEEN -> List.of(
                        first(parts.get("from")).orElseThrow(() -> new IllegalArgumentException("Missing [from] for between: " + field)),
                        first(parts.get("to")).orElseThrow(() -> new IllegalArgumentException("Missing [to] for between: " + field))
                );
                case IN -> parseCsv(parts.getOrDefault("values", List.of()))
                        .orElseGet(() -> parts.getOrDefault("value", List.of()));
                default -> parts.getOrDefault("value", List.of());
            };

            if (rawValues.isEmpty()) {
                throw new IllegalArgumentException("Missing value(s) for field: " + field);
            }

            result.add(new Filter(field, op, rawValues));
        }

        return result;
    }

    private static void put(Map<String, Map<String, List<String>>> tmp, String field, String kind, List<String> vals) {
        tmp.computeIfAbsent(field, k -> new HashMap<>()).put(kind, vals);
    }

    private static Optional<String> first(List<String> list) {
        return (list == null || list.isEmpty()) ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    private static Optional<List<String>> parseCsv(List<String> list) {
        if (list == null || list.isEmpty()) return Optional.empty();
        // bierzemy pierwszy element i rozbijamy po przecinku
        String first = list.get(0);
        if (first == null || first.isBlank()) return Optional.empty();
        return Optional.of(Arrays.stream(first.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList());
    }
}
