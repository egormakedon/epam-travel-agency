package repository;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

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

    @Override
    public Set get() {
        return setOfCollections;
    }

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