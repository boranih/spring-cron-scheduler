package com.borani.spring.boot.scheduler.event;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

public enum Event {

    REMITTANCE_INFORMATION_PUBLISHED_EVENT,
    CACHE_REFRESH_EVENT;

    private static final Map<String, Event> NAME_TO_EVENT;

    static {
        NAME_TO_EVENT = stream(Event.values())
                .collect(Collectors.toMap(event -> event.name().toLowerCase(), event -> event));
    }

    public static Optional<Event> fromString(String eventName) {
        return ofNullable(NAME_TO_EVENT.get(eventName));
    }
}
