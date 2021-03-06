package com.epam.web.repository.impl.user;

import com.epam.web.entity.user.User;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;
import com.epam.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    private final static String TABLE_NAME = "user";

    @Autowired
    public UserRepositoryImpl(@Qualifier("baseConnectionPool")DataSource dataSource,
                              RowMapper<User> rowMapper) {
        super(dataSource, rowMapper);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        String name = user.getName();
        if (name != null) {
            preparedStatement.setString(1, name);
        }
        String email = user.getEmail();
        if (email != null) {
            preparedStatement.setString(2, email);
        }
        String login = user.getLogin();
        if (StringUtils.isNotEmpty(login)) {
            preparedStatement.setString(3, login);
        }
        String password = user.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            preparedStatement.setString(4, password);
        }

        preparedStatement.setInt(5, user.getLoyaltyPoints());
        preparedStatement.setBoolean(6, user.isBlocked());
        preparedStatement.setString(7, user.getRole().name());
        return preparedStatement;
    }

    @Override
    public List<String> getFields() {
        return new ArrayList<>(Arrays.asList("name", "e_mail", "login", "password", "loyalty_points", "blocked", "role"));
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }
}


