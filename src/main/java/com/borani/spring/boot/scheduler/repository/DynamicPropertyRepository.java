package com.borani.spring.boot.scheduler.repository;

import com.borani.spring.boot.scheduler.entity.DynamicPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicPropertyRepository extends JpaRepository<DynamicPropertyEntity, Integer> {
}
