package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface TourMongodbRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 6.0
 */
public interface TourMongodbRepository extends MongoRepository<Tour, Long> {}