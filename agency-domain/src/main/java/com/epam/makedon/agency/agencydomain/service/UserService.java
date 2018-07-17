package com.epam.makedon.agency.agencydomain.service;

import com.epam.makedon.agency.agencydomain.domain.impl.User;

import java.util.Optional;

/**
 * This interface markup services for {@link User} class,
 * extends {@link Service} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public interface UserService extends Service<User> {

    Optional<User> findByUsername(String username);
}