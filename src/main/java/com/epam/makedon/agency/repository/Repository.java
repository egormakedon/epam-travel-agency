package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.*;
import com.epam.makedon.agency.entity.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final thread-safe class {@code Repository}, that implemets interface {@code CrudInterface}.
 * Class safe from cloning and reflection-api.
 * Class store collections and methods to interact with it.
 *
 * @author Yahor Makedon
 * @see CrudInterface
 * @see Storage
 * @version 1.0
 * @since version 1.0
 */
public final class Repository implements CrudInterface, Cloneable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);
    private static Repository instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private Set<CrudInterface> setOfCollections = new HashSet<CrudInterface>();
    private CrudInterface<Hotel> hotelCollection = new Storage<Hotel>();
    private CrudInterface<User> userCollection = new Storage<User>();
    private CrudInterface<Tour> tourCollection = new Storage<Tour>();
    private CrudInterface<Review> reviewCollection = new Storage<Review>();
    private CrudInterface<Country> countryCollection = new Storage<Country>();
    private CrudInterface<TourType> tourTypeCollection = new Storage<TourType>();

    /**
     * @throws RepositoryException if tried to clone with reflection-api.
     */
    private Repository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone connection pool with reflection api");
            throw new RepositoryException("Tried to clone connection pool with reflection api");
        }
        setOfCollections.add(hotelCollection);
        setOfCollections.add(tourCollection);
        setOfCollections.add(userCollection);
        setOfCollections.add(reviewCollection);
        setOfCollections.add(countryCollection);
        setOfCollections.add(tourTypeCollection);
    }

    public static Repository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new Repository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    /**
     * @throws RepositoryCloneException protection from cloning
     */
    @Override
    public Object clone() throws RepositoryCloneException {
        LOGGER.error("Tried to clone connection pool");
        throw new RepositoryCloneException("Tried to clone connection pool");
    }

    /**
     * With switch case define com.epam.makedon.agency.entity type and use definite add method on collection.
     *
     * @param entity generic add method
     */
    @Override
    public void add(Entity entity) {
        EntityType entityType = defineEntity(entity);
        switch (entityType) {
            case USER:
                userCollection.add((User)entity);
                break;
            case TOUR_TYPE:
                tourTypeCollection.add((TourType)entity);
                break;
            case TOUR:
                tourCollection.add((Tour)entity);
                break;
            case REVIEW:
                reviewCollection.add((Review)entity);
                break;
            case HOTEL:
                hotelCollection.add((Hotel)entity);
                break;
            case COUNTRY:
                countryCollection.add((Country)entity);
                break;
        }
    }

    /**
     * @return set of all collections
     */
    @Override
    public Set get() {
        return setOfCollections;
    }

    /**
     *
     * @param id of entity
     * @param entityType type to identify storage
     * @return entity
     * @throws RepositoryException unknown entityType
     */
    public Optional<Entity> get(long id, EntityType entityType) {
        switch (entityType) {
            case USER:
                return Optional.ofNullable((((Storage)userCollection).get(id)));
            case TOUR_TYPE:
                return Optional.ofNullable(((Storage)tourTypeCollection).get(id));
            case TOUR:
                return Optional.ofNullable(((Storage)tourCollection).get(id));
            case REVIEW:
                return Optional.ofNullable(((Storage)reviewCollection).get(id));
            case HOTEL:
                return Optional.ofNullable(((Storage)hotelCollection).get(id));
            case COUNTRY:
                return Optional.ofNullable(((Storage)countryCollection).get(id));
            default:
                LOGGER.error("Unknown com.epam.makedon.agency.entity type");
                throw new RepositoryException("Unknown com.epam.makedon.agency.entity type");
        }
    }

    /**
     * @param entityType by which get definite collection.
     * @return set collection of all com.epam.makedon.agency.entity collection inside
     * @throws RepositoryException if com.epam.makedon.agency.entity type not define.
     */
    public Set get(EntityType entityType) {
        switch (entityType) {
            case USER:
                return userCollection.get();
            case TOUR_TYPE:
                return tourTypeCollection.get();
            case TOUR:
                return tourCollection.get();
            case REVIEW:
                return reviewCollection.get();
            case HOTEL:
                return hotelCollection.get();
            case COUNTRY:
                return countryCollection.get();
            default:
                LOGGER.error("Unknown com.epam.makedon.agency.entity type");
                throw new RepositoryException("Unknown com.epam.makedon.agency.entity type");
        }
    }



    /**
     * With switch case define com.epam.makedon.agency.entity type and use definite delete method on collection.
     *
     * @param entity generic delete method
     */
    @Override
    public void delete(Entity entity) {
        EntityType entityType = defineEntity(entity);
        switch (entityType) {
            case USER:
                userCollection.delete((User)entity);
                break;
            case TOUR_TYPE:
                tourTypeCollection.delete((TourType)entity);
                break;
            case TOUR:
                tourCollection.delete((Tour)entity);
                break;
            case REVIEW:
                reviewCollection.delete((Review)entity);
                break;
            case HOTEL:
                hotelCollection.delete((Hotel)entity);
                break;
            case COUNTRY:
                countryCollection.delete((Country)entity);
                break;
        }

    }

    /**
     * With switch case define com.epam.makedon.agency.entity type and use definite update method on collection.
     *
     * @param entity generic update method
     */
    @Override
    public void update(Entity entity) {
        EntityType entityType = defineEntity(entity);
        switch (entityType) {
            case USER:
                userCollection.update((User)entity);
                break;
            case TOUR_TYPE:
                tourTypeCollection.update((TourType)entity);
                break;
            case TOUR:
                tourCollection.update((Tour)entity);
                break;
            case REVIEW:
                reviewCollection.update((Review)entity);
                break;
            case HOTEL:
                hotelCollection.update((Hotel)entity);
                break;
            case COUNTRY:
                countryCollection.update((Country)entity);
                break;
        }
    }

    /**
     * @param entity entity type
     * @return Exemplar {@code EntityType}, using instanceof on com.epam.makedon.agency.entity
     * @throws RepositoryException if param is unknown com.epam.makedon.agency.entity.
     */
    private EntityType defineEntity(Entity entity) {
        if (entity instanceof Country) {
            return EntityType.COUNTRY;
        } else if (entity instanceof Hotel) {
            return EntityType.HOTEL;
        } else if (entity instanceof Review) {
            return EntityType.REVIEW;
        } else if (entity instanceof Tour) {
            return EntityType.TOUR;
        } else if (entity instanceof TourType) {
            return EntityType.TOUR_TYPE;
        } else if (entity instanceof User) {
            return EntityType.USER;
        } else {
            LOGGER.error("Unknown com.epam.makedon.agency.entity type");
            throw new RepositoryException("Unknown com.epam.makedon.agency.entity type");
        }
    }
}