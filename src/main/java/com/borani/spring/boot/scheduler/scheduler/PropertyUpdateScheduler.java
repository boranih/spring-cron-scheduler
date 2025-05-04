package com.borani.spring.boot.scheduler.scheduler;

import com.borani.spring.boot.scheduler.event.Event;
import com.borani.spring.boot.scheduler.event.PropertyChangeEvent;
import com.borani.spring.boot.scheduler.repository.DynamicPropertyRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class PropertyUpdateScheduler implements SchedulingConfigurer {

    private final DynamicPropertyRepository propertyRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final ConcurrentHashMap<String, String> propertyHashMap = new ConcurrentHashMap<>();

    private final String cronExpression = "0/10 * * * * *";  // Default: every 10 seconds

    public PropertyUpdateScheduler(DynamicPropertyRepository propertyRepository, ApplicationEventPublisher eventPublisher) {
        this.propertyRepository = propertyRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Callback allowing a {@link TaskScheduler}
     * and specific {@link Task} instances
     * to be registered against the given the {@link ScheduledTaskRegistrar}.
     *
     * @param taskRegistrar the registrar to be configured
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.setScheduler(taskExecutor());

        taskRegistrar.addTriggerTask(this::updateProperty, triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);
            return cronTrigger.nextExecution(triggerContext);
        });

    }

    private void updateProperty() {
        // Collect changes in a list for batch publishing (optional, depending on your use case)
        List<PropertyChangeEvent> eventsToPublish = new ArrayList<>();

        propertyRepository.findAll().forEach(property -> {
            String propKey = property.getPropKey();
            String newValue = property.getPropValue();
            String oldValue = propertyHashMap.get(propKey);
            Optional<Event> eventName = Event.fromString(property.getPropIdentifier());
            // Check if the property has changed and if the event is valid
            if (oldValue != null && !oldValue.equals(newValue) && eventName.isPresent()) {
                // Add event to the batch list instead of directly publishing
                eventsToPublish.add(new PropertyChangeEvent(this, eventName.get(), propKey, newValue));
            }
            propertyHashMap.put(propKey, newValue); // Update property map
        });

        // Publish events in a batch (if any changes occurred)
        if (!eventsToPublish.isEmpty()) {
            eventsToPublish.forEach(eventPublisher::publishEvent);
        }
    }

    private Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}