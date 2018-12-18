package com.epam.web.entity;

import com.epam.web.entity.enums.UserRole;

public class UserBuilder {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private int loyaltyPoints;
    private boolean blocked;
    private UserRole role;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    public UserBuilder setBlocked(boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public UserBuilder setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public User createUser() {
        return new User(id, name, email, login, password, loyaltyPoints, blocked, role);
    }
}