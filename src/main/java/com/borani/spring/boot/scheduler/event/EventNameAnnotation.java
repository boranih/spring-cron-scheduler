package com.borani.spring.boot.scheduler.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventNameAnnotation {

    Event value(); // Event name

}
