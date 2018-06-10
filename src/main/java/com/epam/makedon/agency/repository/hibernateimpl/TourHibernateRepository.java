package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.repository.TourRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Class TourHibernateRepository implements TourRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class TourHibernateRepository implements TourRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public TourHibernateRepository() {}

    /**
     * @param tour object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Tour tour) {
        entityManager.persist(tour);
        return true;
    }

    /**
     * @param id to define and find tour in repository
     * @return tour object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Tour> get(long id) {
        final String NATIVE_QUERY_SELECT_TOUR_BY_ID = "SELECT * FROM tour WHERE tour_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_TOUR_BY_ID, Tour.class);
        query.setParameter(1, id);
        return Optional.ofNullable((Tour)query.getSingleResult());
    }

    /**
     * @param tour object to remove from repository
     * @return boolean result of tour removing from repository
     */
    @Override
    public boolean remove(Tour tour) {
        final String NAMED_QUERY_DELETE_TOUR_BY_ID = "DELETE FROM Tour WHERE id=:tourId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_TOUR_BY_ID);
        query.setParameter("tourId", tour.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * @param tour object to update in repository
     * @return updated tour object, wrapped in optional
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        return Optional.ofNullable(entityManager.merge(tour));
    }
}
