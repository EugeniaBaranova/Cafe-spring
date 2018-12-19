package com.epam.web.service.impl;

import com.epam.web.entity.User;
import com.epam.web.entity.UserBuilder;
import com.epam.web.repository.Repository;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;
import com.epam.web.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private Repository<User> userRepository = mock(UserRepository.class);

    private UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
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
