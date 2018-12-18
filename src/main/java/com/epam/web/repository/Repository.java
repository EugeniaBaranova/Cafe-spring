package com.epam.web.repository;

import com.epam.web.entity.Entity;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {

    T add(T object) throws RepositoryException;

    void remove(T object) throws RepositoryException;

    T update(T object) throws RepositoryException;

    Optional<T> queryForSingleResult(Specification specification) throws RepositoryException;

    List<T> query(Specification specification) throws RepositoryException;

}
