package com.borani.spring.boot.scheduler.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PropertyChangeEvent extends ApplicationEvent {

    private final String propertyKey;
    private final String propertyValue;
    private final Event eventName;

    public PropertyChangeEvent(Object source, Event eventName, String propertyKey, String propertyValue) {
        super(source);
        this.eventName = eventName;
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
    }
}
