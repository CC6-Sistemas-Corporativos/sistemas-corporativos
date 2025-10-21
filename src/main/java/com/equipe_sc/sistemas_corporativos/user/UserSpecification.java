package com.equipe_sc.sistemas_corporativos.user;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> byName(String name) {
        return (root, criteriaQuery, cb) ->
                name == null ? null : cb.like(root.get("name"), "%" + name.toUpperCase() + "%");
    }
    
    public static Specification<User> byPhone(String phone) {
        return (root, query, cb) ->
                phone == null ? null : cb.like(root.get("phone"), "%" + phone.toUpperCase() + "%");
    }

    public static Specification<User> byUsername(String username) {
        return (root, query, cb) ->
                username == null ? null : cb.like(root.get("username"), "%" + username.toUpperCase() + "%");
    }

    public static Specification<User> afterCreatedAt(LocalDate date) {
        return (root, query, cb) ->
                date == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), date.atStartOfDay());
    }

    public static Specification<User> beforeCreatedAt(LocalDate date) {
        return (root, query, cb) ->
                date == null ? null : cb.lessThanOrEqualTo(root.get("createdAt"), date.atStartOfDay());
    }

}
