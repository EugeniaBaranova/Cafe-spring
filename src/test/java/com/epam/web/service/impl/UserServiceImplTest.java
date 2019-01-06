package com.epam.web.service.impl;

import com.epam.web.entity.user.User;
import com.epam.web.entity.user.UserBuilder;
import com.epam.web.repository.Repository;
import com.epam.web.repository.impl.user.UserRepositoryImpl;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.service.impl.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private Repository<User> userRepository = mock(UserRepositoryImpl.class);

    private UserServiceImpl userService = new UserServiceImpl();

    @Test
    @Ignore
    public void shouldLoginAndReturnUserOptionalWhenLoginAndPasswordIsNotNull() throws ServiceException, RepositoryException {
        //given
        String testLogin = "login";
        String testPassword = "12345password";
        User testUser = new UserBuilder().createUser();
        when(userRepository.queryForSingleResult(any(Specification.class))).thenReturn(Optional.of(testUser));
        //when
        Optional<User> result = userService.login(testLogin, testPassword);
        //then
        Assert.assertTrue(result.isPresent());
        Assert.assertThat(result.get(), is(testUser));
    }
}
