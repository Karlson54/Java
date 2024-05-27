package com.example.finance.controller.faculty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.finance.dto.faculty.FacultyDTO;
import com.example.finance.service.faculty.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<FacultyDTO> getAllFaculties() {
        return facultyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long id) {
        FacultyDTO facultyDTO = facultyService.findById(id);
        if (facultyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyDTO);
    }

    @PostMapping
    public ResponseEntity<FacultyDTO> createFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO createdFaculty = facultyService.create(facultyDTO);
        return ResponseEntity.ok(createdFaculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyDTO> updateFaculty(@PathVariable Long id, @RequestBody FacultyDTO facultyDTO) {
        FacultyDTO updatedFaculty = facultyService.update(id, facultyDTO);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
