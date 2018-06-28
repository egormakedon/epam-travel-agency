package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface ReviewMongodbRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 6.0
 */
public interface ReviewMongodbRepository extends MongoRepository<Review, Long> {}