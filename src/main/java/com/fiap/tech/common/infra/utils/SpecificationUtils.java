package com.fiap.tech.common.infra.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> equal(final String prop, final String term) {
        return (root, query, cb) -> cb.equal(root.get(prop), term);
    }

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop)), "%" + term.toUpperCase() + "%");
    }

    public static <T> Specification<T> notEqual(final String prop, final Object value) {
        return (root, query, cb) -> cb.notEqual(root.get(prop), value);
    }
}
