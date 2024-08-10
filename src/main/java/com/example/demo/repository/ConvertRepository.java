package com.example.demo.repository;

import com.example.demo.model.entity.ConvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConvertRepository extends JpaRepository<ConvertEntity, String>, JpaSpecificationExecutor<ConvertEntity> {

}
