package com.epam.web.service.impl.user;

import com.epam.web.entity.SavingResult;
import com.epam.web.entity.user.User;
import com.epam.web.entity.validation.Error;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.repository.Repository;
import com.epam.web.repository.RepositoryFactory;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.user.CheckUniqueLoginAndEmailSpec;
import com.epam.web.repository.specification.user.UserByLoginAndPasswordSpec;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.service.impl.BaseServiceImpl;
import com.epam.web.service.validation.Validator;
import com.epam.web.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Component
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Autowired
    public UserServiceImpl(RepositoryFactory repositoryFactory,
                           Validator<User> validator,
                           Repository<User> mainRepository) {
        super(repositoryFactory, validator, mainRepository);
    }


    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        try {
            if (login != null & password != null) {
                String hashedPassword = encodePassword(password);
                return getMainRepository().queryForSingleResult(
                        new UserByLoginAndPasswordSpec(login, hashedPassword));
            }
            return Optional.empty();
        } catch (Exception e) {
            String errorMessage = getErrorFormatter()
                    .format("[login] Exception while execution method. Method parameters:'%s', '%s'", login, password)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public SavingResult<User> register(User user) throws ServiceException {
        try {
            Optional<Set<Error>> validationError = validateUser(user);
            if (validationError.isPresent()) {
                return new SavingResult<>(validationError.get());
            }
            String hashedPassword = encodePassword(user.getPassword());
            user.setPassword(hashedPassword);
            User savedUser = getMainRepository().add(user);
            return new SavingResult<>(savedUser);
        } catch (Exception e) {
            String errorMessage = getErrorFormatter()
                    .format("[register] Exception while execution method. User to save:'%s'", user)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public SavingResult<User> editProfileInfo(Long id, User newUser) throws ServiceException {
        try {
            logger.debug("[editProfileInfo] Start to edit user profile. User id :{}", id);
            SavingResult<User> update = super.update(newUser);
            if (update.hasError()) {
                logger.debug("[editProfileInfo] Finish to edit user profile with validation error. User id:{}, errors:{}", id, update.getErrors());
            }
            logger.debug("[editProfileInfo] Finish to edit user profile. User id :{}", id);
            return update;
        } catch (Exception e) {
            logger.error("[editProfileInfo] Exception while execution edit user profile", e);
            throw new ServiceException(e);
        }

    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] hash = digest.digest();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            logger.error("[encodePassword] Exception while encoding password.", e);
        }
        return StringUtils.empty();
    }

    private Formatter getErrorFormatter() {
        //using new instance every call of the method cause java.util.Formatter is not thread safe class.
        return new Formatter();
    }

    private SavingResult<User> getNotUniqueUserResult() {
        Error error = new Error();
        error.setMessage("registration.validation.message.not_unique_user");
        return new SavingResult<>(new HashSet<>(Collections.singletonList(error)));
    }

    private boolean isNotUniqueUser(User user) throws RepositoryException, SQLException {
        Repository<User> userRepository = getMainRepository();
        Optional<User> existsUser =
                userRepository
                        .queryForSingleResult(
                                new CheckUniqueLoginAndEmailSpec(
                                        user.getLogin(),
                                        user.getEmail()));
        return existsUser.isPresent();
    }

    private Optional<Set<Error>> validateUser(User user) throws RepositoryException, SQLException {
        ValidationResult result = getValidator().validate(user);
        if (result.hasError()) {
            return Optional.of(result.getErrors());
        }
        if (isNewUser(user)) {
            if (isNotUniqueUser(user)) {
                return Optional.of(getNotUniqueUserResult().getErrors());
            }
        }
        return Optional.empty();
    }

    private boolean isNewUser(User user) {
        return user.getId() == null;
    }

    @Override
    protected Repository<User> getRepository(DataSource dataSource) {
        return getRepositoryFactory()
                .newInstance(UserRepository.class,
                        dataSource);
    }
}
