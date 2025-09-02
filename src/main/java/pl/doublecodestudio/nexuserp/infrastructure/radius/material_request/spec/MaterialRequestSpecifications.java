package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.spec;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence.JpaMaterialRequestEntity;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.Filter;
import pl.doublecodestudio.nexuserp.interfaces.web.filter.FilterOp;

import java.time.Instant;
import java.util.*;

@Component
public class MaterialRequestSpecifications {
    private static final Map<String, String> ALLOWED_FIELDS = Map.ofEntries(
            Map.entry("batchId", "batchId"),
            Map.entry("stageId", "stageId"),
            Map.entry("finalProductId", "finalProductId"),
            Map.entry("finalProductName", "finalProductName"),
            Map.entry("unitId", "unitId"),
            Map.entry("status", "status"),
            Map.entry("shippingDate", "shippingDate"),
            Map.entry("deliveryDate", "deliveryDate"),
            Map.entry("releaseDate", "releaseDate"),
            Map.entry("client", "client")
    );

    public Specification<JpaMaterialRequestEntity> build(List<Filter> filters) {
        Filter forcedStatusFilter = new Filter("status", FilterOp.IN, List.of("NEW"));

        List<Filter> allFilters = new ArrayList<>(filters != null ? filters : List.of());
        allFilters.add(forcedStatusFilter);

        return allFilters.stream()
                .map(f -> {
                    String entityField = ALLOWED_FIELDS.get(f.field());
                    if (entityField == null) {
                        throw new IllegalArgumentException("Unsupported filter field: " + f.field());
                    }
                    return toPredicate(entityField, f);
                })
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private Specification<JpaMaterialRequestEntity> toPredicate(String entityField, Filter f) {
        return (root, query, cb) -> {
            Path<?> path = root.get(entityField);
            Class<?> type = path.getJavaType();

            // Konwersje typów z rawValues
            switch (f.op()) {
                case EQ -> {
                    Object v = convert(type, f.rawValues().get(0));
                    return cb.equal(path, v);
                }
                case CONTAINS -> {
                    if (!String.class.equals(type)) {
                        throw new IllegalArgumentException("Operator 'contains' dostępny tylko dla String (" + entityField + ")");
                    }
                    String v = "%" + f.rawValues().get(0).toLowerCase(Locale.ROOT) + "%";
                    return cb.like(cb.lower(root.get(entityField)), v);
                }
                case IN -> {
                    CriteriaBuilder.In<Object> in = cb.in(path);
                    for (String raw : f.rawValues()) {
                        in.value(convert(type, raw));
                    }
                    return in;
                }
                case GT -> {
                    return buildCompare(cb, path, type, f.rawValues().get(0), CompareOp.GT);
                }
                case GTE -> {
                    return buildCompare(cb, path, type, f.rawValues().get(0), CompareOp.GTE);
                }
                case LT -> {
                    return buildCompare(cb, path, type, f.rawValues().get(0), CompareOp.LT);
                }
                case LTE -> {
                    return buildCompare(cb, path, type, f.rawValues().get(0), CompareOp.LTE);
                }
                case BETWEEN -> {
                    String from = f.rawValues().get(0);
                    String to = f.rawValues().get(1);
                    if (Instant.class.equals(type)) {
                        Instant a = Instant.parse(from);
                        Instant b = Instant.parse(to);
                        return cb.between(root.get(entityField), a, b);
                    } else if (Number.class.isAssignableFrom(type)) {
                        Number a = (Number) convert(type, from);
                        Number b = (Number) convert(type, to);
                        Expression<Number> exp = root.get(entityField);
                        return cb.and(cb.ge(exp, a.doubleValue()), cb.le(exp, b.doubleValue()));
                    } else if (Comparable.class.isAssignableFrom(type)) {
                        @SuppressWarnings("unchecked")
                        Expression<? extends Comparable> exp = root.get(entityField);
                        Comparable a = (Comparable) convert(type, from);
                        Comparable b = (Comparable) convert(type, to);
                        return cb.between(exp, a, b);
                    } else {
                        throw new IllegalArgumentException("BETWEEN nieobsługiwany dla typu " + type.getSimpleName());
                    }
                }
                default -> throw new IllegalStateException("Nieobsługiwany operator: " + f.op());
            }
        };
    }

    private enum CompareOp {GT, GTE, LT, LTE}

    private Predicate buildCompare(CriteriaBuilder cb, Path<?> path, Class<?> type, String raw, CompareOp op) {
        if (Instant.class.equals(type)) {
            Instant v = Instant.parse(raw);
            Path<Instant> p = cast(path);
            return switch (op) {
                case GT -> cb.greaterThan(p, v);
                case GTE -> cb.greaterThanOrEqualTo(p, v);
                case LT -> cb.lessThan(p, v);
                case LTE -> cb.lessThanOrEqualTo(p, v);
            };
        } else if (Number.class.isAssignableFrom(type)) {
            Number n = (Number) convert(type, raw);
            Expression<Number> exp = cast(path);
            return switch (op) {
                case GT -> cb.gt(exp, n.doubleValue());
                case GTE -> cb.ge(exp, n.doubleValue());
                case LT -> cb.lt(exp, n.doubleValue());
                case LTE -> cb.le(exp, n.doubleValue());
            };
        } else if (Comparable.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            Path<Comparable> p = (Path<Comparable>) path;
            Comparable v = (Comparable) convert(type, raw);
            return switch (op) {
                case GT -> cb.greaterThan(p, v);
                case GTE -> cb.greaterThanOrEqualTo(p, v);
                case LT -> cb.lessThan(p, v);
                case LTE -> cb.lessThanOrEqualTo(p, v);
            };
        } else {
            throw new IllegalArgumentException("Porównanie nieobsługiwane dla typu " + type.getSimpleName());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Path<T> cast(Path<?> p) {
        return (Path<T>) p;
    }

    private Object convert(Class<?> type, String raw) {
        if (raw == null) return null;
        if (String.class.equals(type)) return raw;
        if (Instant.class.equals(type)) return Instant.parse(raw);
        if (Integer.class.equals(type) || int.class.equals(type)) return Integer.valueOf(raw);
        if (Long.class.equals(type) || long.class.equals(type)) return Long.valueOf(raw);
        if (Double.class.equals(type) || double.class.equals(type)) return Double.valueOf(raw);
        if (Boolean.class.equals(type) || boolean.class.equals(type)) return Boolean.valueOf(raw);
        // w razie czego:
        return raw;
    }


    public static Specification<JpaMaterialRequestEntity> hasStatus(@Nullable String status) {
        return (root, query, cb) ->
                (status == null || status.isBlank())
                        ? cb.conjunction()
                        : cb.equal(root.get("status"), status);
    }

    public static Specification<JpaMaterialRequestEntity> hasStatuses(@Nullable Collection<String> statuses) {
        return (root, query, cb) ->
                (statuses == null || statuses.isEmpty())
                        ? cb.conjunction()
                        : root.get("status").in(statuses);
    }
}

