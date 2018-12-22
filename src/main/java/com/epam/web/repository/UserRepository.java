package com.epam.web.repository;

import com.epam.web.entity.User;
import com.epam.web.repository.converter.UserConverter;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(new UserConverter());
    }

    @Override
    public User add(User newUser) throws RepositoryException {
        return null;
    }

    @Override
    public void remove(User user) throws RepositoryException {
        return;
    }

    @Override
    public User update(User updatedUser) throws RepositoryException {
        return null;
    }

    public Optional<User> queryForSingleResult(Specification specification) throws RepositoryException {
        if (specification != null) {
            //TODO return
            super.queryForSingleResult(specification);
        }
        return Optional.empty();
    }

    public List<User> query(Specification specification) throws RepositoryException {
        if (specification != null) {
            //TODO return
            super.query(specification);
        }
        return Collections.emptyList();
    }
}
