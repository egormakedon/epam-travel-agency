package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface HotelMongodbRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 6.0
 */
public interface HotelMongodbRepository extends MongoRepository<Hotel, Long> {}