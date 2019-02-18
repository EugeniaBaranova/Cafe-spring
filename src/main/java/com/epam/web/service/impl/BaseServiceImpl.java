package com.epam.web.service.impl;

import com.epam.web.entity.Entity;
import com.epam.web.entity.SavingResult;
import com.epam.web.entity.user.User;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.repository.Repository;
import com.epam.web.repository.RepositoryFactory;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.connection.pool.BaseConnectionPool;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.service.validation.Validator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseServiceImpl<T extends Entity> {

    private RepositoryFactory repositoryFactory;
    private Validator<T> validator;
    private Repository<T> mainRepository;

    public BaseServiceImpl(RepositoryFactory repositoryFactory,
                           Validator<T> validator,
                           Repository<T> mainRepository) {
        this.repositoryFactory = repositoryFactory;
        this.validator = validator;
        this.mainRepository = mainRepository;
    }


    protected SavingResult<T> create(T entity) throws RepositoryException {
        return addOrUpdate(entity, true);
    }


    protected SavingResult<T> update(T entity) throws RepositoryException {
        return addOrUpdate(entity, false);
    }

    private SavingResult<T> addOrUpdate(T entity, boolean saveNew) throws RepositoryException {
        ValidationResult validationResult = getValidator().validate(entity);
        if (validationResult.hasError()) {
            return new SavingResult<>(validationResult.getErrors());
        }

        if (saveNew) {
            T savedNew = getMainRepository().add(entity);
            return new SavingResult<>(savedNew);
        }
        T update = getMainRepository().update(entity);
        return new SavingResult<>(update);
    }


    protected abstract Repository<T> getRepository(DataSource dataSource);


    protected RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    protected Validator<T> getValidator() {
        return validator;
    }

    protected Repository<T> getMainRepository() {
        return mainRepository;
    }
}
