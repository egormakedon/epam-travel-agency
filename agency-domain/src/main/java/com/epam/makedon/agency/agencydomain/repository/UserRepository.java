package com.epam.makedon.agency.agencydomain.repository;

import com.epam.makedon.agency.agencydomain.domain.impl.User;

import java.util.Optional;

/**
 * This interface markup repositories for {@link User} class,
 * extends {@link Repository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);
}