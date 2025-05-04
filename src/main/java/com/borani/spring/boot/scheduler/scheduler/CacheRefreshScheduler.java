package com.borani.spring.boot.scheduler.scheduler;

import com.borani.spring.boot.scheduler.event.EventNameAnnotation;
import com.borani.spring.boot.scheduler.event.PropertyChangeHandler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.borani.spring.boot.scheduler.event.Event.CACHE_REFRESH_EVENT;

@Component
@EventNameAnnotation(CACHE_REFRESH_EVENT)
public class CacheRefreshScheduler implements SchedulingConfigurer, PropertyChangeHandler {

    private String cronExpression = "0/10 * * * * *";  // Default: every 10 seconds

    private ScheduledTaskRegistrar taskRegistrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;

        taskRegistrar.setScheduler(taskExecutor());

        taskRegistrar.addTriggerTask(() -> {
            //Refresh the cache at certain time
        }, triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);
            return cronTrigger.nextExecution(triggerContext);
        });
    }

    private Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void handleChange(String propertyKey, String newValue) {
        this.cronExpression = newValue;
        // Must re-register tasks
        if (taskRegistrar != null) {
            taskRegistrar.destroy();
            configureTasks(taskRegistrar);
        }
    }
}
