package repository;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final thread-safe class {@code Repository}, that implemets interface {@code Collection}.
 * Class safe from cloning and reflection-api.
 * Class store collections and methods to interact with it.
 *
 * @author Yahor Makedon
 * @see repository.Collection
 * @see repository.CollectionSet
 * @version 1.0
 * @since version 1.0
 */
public final class Repository implements Collection {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);
    private static Repository instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private Set<Collection> setOfCollections = new HashSet<Collection>();
    private Collection<Hotel> hotelCollection = new CollectionSet<Hotel>();
    private Collection<User> userCollection = new CollectionSet<User>();
    private Collection<Tour> tourCollection = new CollectionSet<Tour>();
    private Collection<Review> reviewCollection = new CollectionSet<Review>();
    private Collection<Country> countryCollection = new CollectionSet<Country>();
    private Collection<TourType> tourTypeCollection = new CollectionSet<TourType>();

    /**
     * @throws {@code RuntimeException()}, if tried to clone with reflection-api.
     */
    private Repository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone connection pool with reflection api");
            throw new RuntimeException("Tried to clone connection pool with reflection api");
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone connection pool");
            throw new CloneNotSupportedException("Tried to clone connection pool");
        }
        return super.clone();
    }

    /**
     * With switch case define entity type and use definite add method on collection.
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
     * @param entityType by which get definite collection.
     * @return set collection of all entity collection inside
     * @throws RuntimeException if entity type not define.
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
                LOGGER.error("Unknown entity type");
                throw new RuntimeException("Unknown entity type");
        }
    }

    /**
     * With switch case define entity type and use definite delete method on collection.
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
     * With switch case define entity type and use definite update method on collection.
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
     * @param entity
     * @return Exemplar {@code EntityType}, using instanceof on entity
     * @throws {@code RuntimeException()}, if param is unknown entity.
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
            LOGGER.error("Unknown entity type");
            throw new RuntimeException("Unknown entity type");
        }
    }
}