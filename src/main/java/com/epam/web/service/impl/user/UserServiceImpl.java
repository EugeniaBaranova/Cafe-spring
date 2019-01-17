package com.epam.web.service.impl.user;

import com.epam.web.entity.SavingResult;
import com.epam.web.entity.user.User;
import com.epam.web.entity.validation.Error;
import com.epam.web.repository.Repository;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.user.CheckUniqueLoginAndEmailSpec;
import com.epam.web.repository.specification.user.UserByLoginAndPasswordSpec;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.service.validation.Validator;
import com.epam.web.utils.StringUtils;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private Repository<User> userRepository;
    private Validator<User> validator;
    private ReentrantLock reentrantLock;

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        try {
            if (login != null & password != null) {
                String hashedPassword = encodePassword(password);
                return getUserRepository().queryForSingleResult(new UserByLoginAndPasswordSpec(login, hashedPassword));
            }
            return Optional.empty();
        } catch (RepositoryException e) {
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
            if(validationError.isPresent()){
                return new SavingResult<>(validationError.get());
            }
            String hashedPassword = encodePassword(user.getPassword());
            user.setPassword(hashedPassword);
            User savedUser = getUserRepository().add(user);
            return new SavingResult<>(savedUser);
        } catch (RepositoryException e) {
            String errorMessage = getErrorFormatter()
                    .format("[register] Exception while execution method. User to save:'%s'", user)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public SavingResult<User> editProfileInfo(Long id, User newUser) throws ServiceException {
        return new SavingResult<>(newUser);
    }

    private String encodePassword(String password)  {
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

    private Repository<User> getUserRepository() {
        return userRepository;
    }

    private Formatter getErrorFormatter() {
        //using new instance every call of the method cause java.util.Formatter is not thread safe class.
        return new Formatter();
    }

    private SavingResult<User> getNotUniqueUserResult(){
        Error error = new Error();
        error.setMessage("registration.validation.message.not_unique_user");
        return new SavingResult<>(new HashSet<>(Collections.singletonList(error)));
    }

    private boolean isNotUniqueUser(User user) throws RepositoryException {
        Optional<User> existsUser = getUserRepository()
                .queryForSingleResult(new CheckUniqueLoginAndEmailSpec(user.getLogin(), user.getEmail()));
        return existsUser.isPresent();
    }


    private Optional<Set<Error>> validateUser(User user) throws RepositoryException {
        ValidationResult result = getValidator().validate(user);
        if(result.hasError()){
            return Optional.of(result.getErrors());
        }
        if(isNewUser(user)){
            if(isNotUniqueUser(user)){
                return Optional.of(getNotUniqueUserResult().getErrors());
            }
        }
        return Optional.empty();
    }

    private boolean isNewUser(User user){
        return user.getId() == null;
    }

    private Validator<User> getValidator() {
        return validator;
    }

    public void setUserRepository(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    public void setValidator(Validator<User> validator) {
        this.validator = validator;
    }
}
