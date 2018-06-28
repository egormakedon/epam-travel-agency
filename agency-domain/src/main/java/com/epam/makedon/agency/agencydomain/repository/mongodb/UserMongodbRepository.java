package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.domain.impl.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface UserMongodbRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 6.0
 */
public interface UserMongodbRepository extends MongoRepository<User, Long> {}