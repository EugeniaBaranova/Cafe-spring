package com.epam.web.repository.specification.user;

import com.epam.web.repository.specification.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckUniqueLoginAndEmailSpec implements Specification {

    private String login;
    private String email;

    public CheckUniqueLoginAndEmailSpec(String login, String email) {
        this.login = login;
        this.email = email;
    }

    @Override
    public String toSql() {
        return "SELECT (id) from user WHERE e_mail=? OR login=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(email, login);
    }
}
