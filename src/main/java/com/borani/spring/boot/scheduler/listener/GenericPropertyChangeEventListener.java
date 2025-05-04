package com.borani.spring.boot.scheduler.listener;

import com.borani.spring.boot.scheduler.configuration.PropertyChangeHandlerRegistry;
import com.borani.spring.boot.scheduler.event.PropertyChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class GenericPropertyChangeEventListener {

    private final PropertyChangeHandlerRegistry registry;

    public GenericPropertyChangeEventListener(PropertyChangeHandlerRegistry registry) {
        this.registry = registry;
    }

    @EventListener
    public void handlePropertyChange(PropertyChangeEvent event) {
        ofNullable(registry.getPropertyChangeHandler(event.getEventName()))
                .ifPresent(handler -> handler.handleChange(event.getPropertyKey(), event.getPropertyValue()));
    }
}
