package com.epam.web.service.impl;

import com.epam.web.entity.Entity;
import com.epam.web.entity.SavingResult;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.repository.Repository;
import com.epam.web.repository.RepositoryFactory;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.service.validation.Validator;

public abstract class BaseServiceImpl<T extends Entity> {

    private RepositoryFactory repositoryFactory;
    private RepositorySource repositorySource;
    private Validator<T> validator;

    public BaseServiceImpl(RepositoryFactory repositoryFactory,
                           RepositorySource repositorySource,
                           Validator<T> validator) {
        this.repositoryFactory = repositoryFactory;
        this.repositorySource = repositorySource;
        this.validator = validator;
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
            T savedNew = getRepository().add(entity);
            return new SavingResult<>(savedNew);
        }
        T update = getRepository().update(entity);
        return new SavingResult<>(update);

    }


    protected abstract Repository<T> getRepository();


    protected RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    protected RepositorySource getRepositorySource() {
        return repositorySource;
    }

    protected Validator<T> getValidator() {
        return validator;
    }
}
