package com.epam.web.repository.specification.user;

import com.epam.web.repository.specification.Specification;

import java.util.Arrays;
import java.util.List;

public class UserByLoginAndPasswordSpec implements Specification {

    private String login;
    private String password;

    public UserByLoginAndPasswordSpec(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toSql() {
        return "SELECT id, name, login, password, blocked,e_mail,loyalty_points, role FROM user WHERE login = ? AND password = ?";
    }

    @Override
    public List<Object> getParameters() {
            return Arrays.asList(login, password);
    }
}
