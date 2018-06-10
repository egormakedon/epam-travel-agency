package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Class HotelHibernateRepository implements HotelRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class HotelHibernateRepository implements HotelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public HotelHibernateRepository() {}

    /**
     * @param hotel object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Hotel hotel) {
        entityManager.persist(hotel);
        return true;
    }

    /**
     * @param id to define and find hotel in repository
     * @return hotel object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Hotel> get(long id) {
        final String NATIVE_QUERY_SELECT_HOTEL_BY_ID = "SELECT * FROM hotel WHERE hotel_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_HOTEL_BY_ID, Hotel.class);
        query.setParameter(1, id);
        return Optional.ofNullable((Hotel)query.getSingleResult());
    }

    /**
     * @param hotel object to remove from repository
     * @return boolean result of hotel removing from repository
     */
    @Override
    public boolean remove(Hotel hotel) {
        final String NAMED_QUERY_DELETE_HOTEL_BY_ID = "DELETE FROM Hotel WHERE id=:hotelId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_HOTEL_BY_ID);
        query.setParameter("hotelId", hotel.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * @param hotel object to update in repository
     * @return updated hotel object, wrapped in optional
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        return Optional.ofNullable(entityManager.merge(hotel));
    }
}
