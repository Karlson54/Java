package com.example.finance.repository;

import com.example.finance.model.Mark;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    Collection<Mark> findAllByStudentIdAndControlDisciplineId(Long studentId, Long disciplineId);
}