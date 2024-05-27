package com.example.finance.controller.control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.finance.dto.control.ControlDTO;
import com.example.finance.dto.mark.MarkDTO;
import com.example.finance.service.control.ControlService;

import java.util.List;

@RestController
@RequestMapping("/api/controls")
public class ControlController {
    private final ControlService controlService;

    public ControlController(ControlService controlService) {
        this.controlService = controlService;
    }

    @GetMapping
    public List<ControlDTO> getAllControls() {
        return controlService.findAllControls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ControlDTO> getControlById(@PathVariable Long id) {
        ControlDTO controlDTO = controlService.findControlById(id);
        if (controlDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(controlDTO);
    }

    @PostMapping
    public ResponseEntity<ControlDTO> createControl(@RequestBody ControlDTO controlDTO) {
        ControlDTO createdControl = controlService.createControl(controlDTO);
        return ResponseEntity.ok(createdControl);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ControlDTO> updateControl(@PathVariable Long id, @RequestBody ControlDTO controlDTO) {
        ControlDTO updatedControl = controlService.updateControl(id, controlDTO);
        if (updatedControl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedControl);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControl(@PathVariable Long id) {
        controlService.deleteControl(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/marks")
    public ResponseEntity<MarkDTO> createMark(@RequestBody MarkDTO markDTO) {
        MarkDTO createdMark = controlService.createMark(markDTO);
        return ResponseEntity.ok(createdMark);
    }

    @GetMapping("/students/{studentId}/disciplines/{disciplineId}/marks")
    public List<MarkDTO> getMarksByStudentAndDiscipline(@PathVariable Long studentId, @PathVariable Long disciplineId) {
        return controlService.findMarksByStudentAndDiscipline(studentId, disciplineId);
    }

    @GetMapping("/students/{studentId}/controls")
    public List<ControlDTO> getControlsByStudent(@PathVariable Long studentId) {
        return controlService.findControlsByStudent(studentId);
    }
}