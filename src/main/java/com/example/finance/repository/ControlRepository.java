package com.example.finance.repository;

import com.example.finance.model.Control;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlRepository extends JpaRepository<Control, Long> {
    Collection<Control> findAllByGroupId(Long studentId);
}