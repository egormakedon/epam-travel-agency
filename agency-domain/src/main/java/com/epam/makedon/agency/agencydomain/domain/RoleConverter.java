package com.epam.makedon.agency.agencydomain.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This class is set mapping between {@link Role} class and database using hibernate framework,
 * implements {@link AttributeConverter} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Converter

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