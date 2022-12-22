package com.erasmatov.crudapp.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();
    T getById(ID id);
    void deleteById(ID id);
    T save(T t);
    T updated(T t);
}
