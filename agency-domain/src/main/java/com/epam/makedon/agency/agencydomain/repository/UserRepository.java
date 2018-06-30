package com.epam.makedon.agency.agencydomain.repository;

import com.epam.makedon.agency.agencydomain.domain.impl.User;

import java.util.Optional;

/**
 * Interface UserRepository mark repository with User domain.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 1.0
 */
public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);
}