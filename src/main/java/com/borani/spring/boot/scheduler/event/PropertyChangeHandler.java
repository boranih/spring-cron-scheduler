package com.borani.spring.boot.scheduler.event;

public interface PropertyChangeHandler {

    void handleChange(String propertyKey, String newValue);

}
