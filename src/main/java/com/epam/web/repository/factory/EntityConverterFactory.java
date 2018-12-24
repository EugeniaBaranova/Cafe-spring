package com.epam.web.repository.factory;

import com.epam.web.entity.Product;
import com.epam.web.entity.User;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.converter.ProductConverter;
import com.epam.web.repository.converter.UserConverter;
import com.epam.web.repository.exception.UnsupportedFactoryTypeException;

import java.util.HashMap;
import java.util.Map;

public final class EntityConverterFactory {
    private static EntityConverterFactory INSTANCE = new EntityConverterFactory();
    private Converter<Product> productConverter = new ProductConverter();
    private Converter<User> userConverter = new UserConverter();
    private final Map<Class<? extends Converter>, Converter> converters
            = new HashMap<>();

    private EntityConverterFactory() {
        converters.put(ProductConverter.class, productConverter);
        converters.put(UserConverter.class, userConverter);
    }

    public static EntityConverterFactory getInstance(){
        return INSTANCE;
    }

    public <T> T getConverter(Class<T> converterClass){
        Converter converter = converters.get(converterClass);
        if(converter == null){
            throw new UnsupportedFactoryTypeException(converterClass);
        }
        return (T) converter;

    }


}
