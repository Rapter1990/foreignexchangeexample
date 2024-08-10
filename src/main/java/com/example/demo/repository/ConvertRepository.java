package com.example.demo.repository;

import com.example.demo.model.entity.ConvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing {@link ConvertEntity} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and {@link JpaSpecificationExecutor} to support dynamic queries based on specifications.
 */
public interface ConvertRepository extends JpaRepository<ConvertEntity, String>, JpaSpecificationExecutor<ConvertEntity> {

}
