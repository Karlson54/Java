package com.example.finance.service.control;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.dto.control.ControlDTO;
import com.example.finance.dto.mark.MarkDTO;
import com.example.finance.mapper.control.ControlMapper;
import com.example.finance.mapper.mark.MarkMapper;
import com.example.finance.model.Control;
import com.example.finance.model.Discipline;
import com.example.finance.model.Group;
import com.example.finance.model.Mark;
import com.example.finance.model.StudentProfile;
import com.example.finance.repository.ControlRepository;
import com.example.finance.repository.DisciplineRepository;
import com.example.finance.repository.GroupRepository;
import com.example.finance.repository.MarkRepository;
import com.example.finance.repository.StudentProfileRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ControlService {
    private final ControlRepository controlRepository;
    private final DisciplineRepository disciplineRepository;
    private final GroupRepository groupRepository;
    private final MarkRepository markRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ControlMapper controlMapper;
    private final MarkMapper markMapper;

    public List<ControlDTO> findAllControls() {
        return controlRepository.findAll().stream()
                .map(controlMapper::toDto)
                .collect(Collectors.toList());
    }

    public ControlDTO findControlById(Long id) {
        Optional<Control> control = controlRepository.findById(id);
        return control.map(controlMapper::toDto).orElse(null);
    }

    public ControlDTO createControl(ControlDTO controlDTO) {
        Discipline discipline = disciplineRepository.findById(controlDTO.getDisciplineId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid discipline ID"));
        Group group = groupRepository.findById(controlDTO.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        Control control = controlMapper.toEntity(controlDTO, discipline, group);
        control = controlRepository.save(control);
        return controlMapper.toDto(control);
    }

    public ControlDTO updateControl(Long id, ControlDTO controlDTO) {
        if (!controlRepository.existsById(id)) {
            return null;
        }
        Discipline discipline = disciplineRepository.findById(controlDTO.getDisciplineId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid discipline ID"));
        Group group = groupRepository.findById(controlDTO.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid group ID"));
        Control control = controlMapper.toEntity(controlDTO, discipline, group);
        control.setId(id);
        control = controlRepository.save(control);
        return controlMapper.toDto(control);
    }

    public void deleteControl(Long id) {
        if (controlRepository.existsById(id)) {
            controlRepository.deleteById(id);
        }
    }

    public MarkDTO createMark(MarkDTO markDTO) {
        Control control = controlRepository.findById(markDTO.getControlId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid control ID"));
        StudentProfile student = studentProfileRepository.findById(markDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
        Mark mark = markMapper.toEntity(markDTO, control, student);
        mark = markRepository.save(mark);
        return markMapper.toDto(mark);
    }

    public List<MarkDTO> findMarksByStudentAndDiscipline(Long studentId, Long disciplineId) {
        return markRepository.findAllByStudentIdAndControlDisciplineId(studentId, disciplineId).stream()
                .map(markMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ControlDTO> findControlsByStudent(Long studentId) {
        return controlRepository.findAllByGroupId(studentId).stream()
                .map(controlMapper::toDto)
                .collect(Collectors.toList());
    }
}
