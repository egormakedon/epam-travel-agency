package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface HotelMongodbRepository,
 * provide spring-data mongodb repository for {@link Review} class,
 * extends {@link MongoRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public interface ReviewMongodbRepository extends MongoRepository<Review, Long> {}