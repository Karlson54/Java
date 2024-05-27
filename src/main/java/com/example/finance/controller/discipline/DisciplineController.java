package com.example.finance.controller.discipline;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.finance.dto.discipline.DisciplineDTO;
import com.example.finance.service.discipline.DisciplineService;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public List<DisciplineDTO> getAllDisciplines() {
        return disciplineService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDisciplineById(@PathVariable Long id) {
        DisciplineDTO disciplineDTO = disciplineService.findById(id);
        if (disciplineDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(disciplineDTO);
    }

    @PostMapping
    public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineDTO disciplineDTO) {
        DisciplineDTO createdDiscipline = disciplineService.create(disciplineDTO);
        return ResponseEntity.ok(createdDiscipline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineDTO> updateDiscipline(@PathVariable Long id, @RequestBody DisciplineDTO disciplineDTO) {
        DisciplineDTO updatedDiscipline = disciplineService.update(id, disciplineDTO);
        if (updatedDiscipline == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDiscipline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        disciplineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
