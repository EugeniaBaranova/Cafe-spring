package com.epam.web.entity;

import com.epam.web.entity.enums.UserRole;

import java.io.Serializable;

public class User extends Entity implements Serializable {

    private String name;
    private String email;
    private String login;
    private String password;
    private int loyaltyPoints;
    private boolean blocked;
    private UserRole role;

    public User(Long id,
                String name,
                String email,
                String login,
                String password,
                int loyaltyPoints,
                boolean blocked,
                UserRole role) {
        super(id);
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.loyaltyPoints = loyaltyPoints;
        this.blocked = blocked;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
