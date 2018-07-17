package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.repository.HotelRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Repository class for {@link Hotel} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainHibernateConfiguration} class,
 * implements {@link HotelRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("hibernateRepository")

public class HotelHibernateRepository implements HotelRepository {

    private static final String HOTEL_ID = "hotelId";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public HotelHibernateRepository() {}

    /**
     * Add operation
     *
     * @param hotel {@link Hotel}
     * @return true/false
     */
    @Override
    public boolean add(Hotel hotel) {
        entityManager.persist(hotel);
        return true;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Hotel> get(long id) {
        final String NATIVE_QUERY_SELECT_HOTEL_BY_ID = "SELECT * " +
                "FROM hotel " +
                "WHERE hotel_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_HOTEL_BY_ID, Hotel.class);
        query.setParameter(1, id);
        return Optional.ofNullable((Hotel)query.getSingleResult());
    }

    /**
     * Remove operation
     *
     * @param hotel {@link Hotel}
     * @return true/false
     */
    @Override
    public boolean remove(Hotel hotel) {
        final String NAMED_QUERY_DELETE_HOTEL_BY_ID = "DELETE " +
                "FROM Hotel " +
                "WHERE id=:hotelId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_HOTEL_BY_ID);
        query.setParameter(HOTEL_ID, hotel.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * Update operation
     *
     * @param hotel {@link Hotel}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        return Optional.ofNullable(entityManager.merge(hotel));
    }
}