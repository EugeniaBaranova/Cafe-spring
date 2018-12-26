package com.epam.web.repository.converter;

import com.epam.web.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Converter<T extends Entity> {

    T convert(ResultSet resultSet) throws SQLException;
}
