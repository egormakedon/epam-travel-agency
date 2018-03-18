package repository;

import entity.Entity;

import java.util.Set;

interface ICollection<T extends Entity> {
    void add(T entity);
    Set<T> get();
    void delete(T entity);
    void update(T entity);
}
