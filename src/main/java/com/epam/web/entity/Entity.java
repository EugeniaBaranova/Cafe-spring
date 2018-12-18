package com.epam.web.entity;

public abstract class Entity {

    private Long id;

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
