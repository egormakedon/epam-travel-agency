package com.epam.makedon.agency.agencydomain.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Role;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, Long> {

    @Override
    public Long convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Long aLong) {
        if (aLong == null) {
            return null;
        }
        return Role.fromCode(aLong);
    }
}