package com.smartgridready.communicator.common.helper;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class DriverFactoryLoader {

    public static <T> T getImplementation(Class<T> clazz) throws NoSuchElementException {
        ServiceLoader<T> services = ServiceLoader.load(clazz);
        return services.findFirst().orElseThrow();
    }
}
