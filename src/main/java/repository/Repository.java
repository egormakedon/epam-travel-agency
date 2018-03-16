package repository;

import entity.Entity;

public interface Repository<T extends Entity> {
    void add(T entity);
    T get();
    void delete(T entity);
    void update(T entity);
}
