package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class TourHibernateRepository implements TourRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class TourHibernateRepository implements TourRepository {

    @PersistenceContext
    private EntityManager entityManager;

    enum Criteria {
        DATE, DURATION, TOUR_TYPE, COST, STARS, COUNTRY
    }

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
        entityManager.merge(tour);
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

    public List<Tour> findByCriteria(LocalDate date, Duration duration, Country country,
                                     Byte stars, TourType type, BigDecimal cost) {
        String sqlFindTourByCriteria = "SELECT * FROM tour t INNER JOIN hotel h ON t.fk_hotel_id=h.hotel_id";

        List<Object> criteriaValuesList = new ArrayList<>();
        if (date != null) {
            sqlFindTourByCriteria = updateSqlQuery(sqlFindTourByCriteria, Criteria.DATE);
            criteriaValuesList.add(date);
        }
        if (duration != null) {
            sqlFindTourByCriteria = updateSqlQuery(sqlFindTourByCriteria, Criteria.DURATION);
            criteriaValuesList.add(duration.toDays());
        }
        if (country != null) {
            sqlFindTourByCriteria = updateSqlQuery(sqlFindTourByCriteria, Criteria.COUNTRY);
            criteriaValuesList.add(country.getId());
        }
        if (stars != null) {
            sqlFindTourByCriteria = updateSqlQuery(sqlFindTourByCriteria, Criteria.STARS);
            criteriaValuesList.add(stars);
        }
        if (type != null) {
            sqlFindTourByCriteria = updateSqlQuery(sqlFindTourByCriteria, Criteria.TOUR_TYPE);
            criteriaValuesList.add(type.getId());
        }
        if (cost != null) {
            sqlFindTourByCriteria = updateSqlQuery(sqlFindTourByCriteria, Criteria.COST);
            criteriaValuesList.add(cost);
        }

        if (!criteriaValuesList.isEmpty()) {
            sqlFindTourByCriteria = sqlFindTourByCriteria.substring(0, sqlFindTourByCriteria.length() - 5);
        }

        Query query = entityManager.createNativeQuery(sqlFindTourByCriteria, Tour.class);
        for (int i = 0; i < criteriaValuesList.size(); i++) {
            query.setParameter(i + 1, criteriaValuesList.get(i));
        }
        return query.getResultList();
    }

    private String updateSqlQuery(String query, Criteria criteria) {
        final String SQL_WHERE_CASE = " WHERE ";
        if (!query.contains(SQL_WHERE_CASE)) {
            query += SQL_WHERE_CASE;
        }
        switch (criteria) {
            case COST:
                query += " t.tour_cost<=? AND ";
                break;
            case DATE:
                query += " t.tour_date=? AND ";
                break;
            case STARS:
                query += " h.hotel_stars=? AND ";
                break;
            case COUNTRY:
                query += " t.fk_country_id=? AND ";
                break;
            case DURATION:
                query += " t.tour_duration=? AND ";
                break;
            case TOUR_TYPE:
                query += " t.fk_tour_type_id=? AND ";
                break;
        }
        return query;
    }
}