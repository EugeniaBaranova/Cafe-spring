package com.epam.web.repository.specification;

import com.epam.web.entity.User;

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
        //TODO check in BD
        return "SELECT id, name, login, password, blocked, role FROM user WHERE login = ? AND password = ?";
    }

    @Override
    public List<String> getParameters() {
        return Arrays.asList(login, password);
    }
}
