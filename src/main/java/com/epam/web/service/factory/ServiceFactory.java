package com.epam.web.service.factory;

import com.epam.web.repository.exception.UnsupportedFactoryTypeException;
import com.epam.web.service.Service;
import java.util.HashMap;
import java.util.Map;

public final class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private Map<Class<? extends Service>,Service> serviceMap = new HashMap<>();
    private ServiceFactory(){}

    public <T> T getService(Class<T> serviceClass){
        Service service = serviceMap.get(serviceClass);
        if(service == null){
            throw new UnsupportedFactoryTypeException(serviceClass);
        }
        return (T)service;
    }

    public static ServiceFactory getInstance(){
        return INSTANCE;
    }

}
