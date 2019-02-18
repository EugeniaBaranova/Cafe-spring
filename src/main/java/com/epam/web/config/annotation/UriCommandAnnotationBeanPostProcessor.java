package com.epam.web.config.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class UriCommandAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final String COMMAND_FACTORY_BEAN_NAME = "commandFactory";

    private Map<String, Object> commands = new HashMap<>();

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        UriCommand annotation = bean.getClass().getAnnotation(UriCommand.class);
        if (annotation != null) {
            String commandName = annotation.name();
            commands.put(commandName, bean);
        }
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(COMMAND_FACTORY_BEAN_NAME.equals(beanName)){
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                Class<?> fieldType = field.getType();
                if (Map.class.equals(fieldType)){
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, commands);
                }
            }
        }
        return bean;
    }
}
