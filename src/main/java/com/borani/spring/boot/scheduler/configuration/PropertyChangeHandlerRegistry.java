package com.borani.spring.boot.scheduler.configuration;

import com.borani.spring.boot.scheduler.event.Event;
import com.borani.spring.boot.scheduler.event.EventNameAnnotation;
import com.borani.spring.boot.scheduler.event.PropertyChangeHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
public class PropertyChangeHandlerRegistry {

    private Map<Event, PropertyChangeHandler> handlerMap = new HashMap<>();

    private final List<PropertyChangeHandler> handlers;

    public PropertyChangeHandlerRegistry(List<PropertyChangeHandler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    public void initialize() {
        this.handlerMap = handlers.stream()
                .filter(handler -> handler.getClass().getAnnotation(EventNameAnnotation.class) != null)
                .collect(Collectors
                        .toMap(
                                handler -> requireNonNull(handler.getClass().getAnnotation(EventNameAnnotation.class)).value(),
                                handler -> handler));

    }

    public PropertyChangeHandler getPropertyChangeHandler(Event eventName) {
        return handlerMap.get(eventName);
    }
}
