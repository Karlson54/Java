package com.example.finance.service.discipline;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.dto.discipline.DisciplineDTO;
import com.example.finance.mapper.discipline.DisciplineMapper;
import com.example.finance.model.Discipline;
import com.example.finance.model.Faculty;
import com.example.finance.repository.DisciplineRepository;
import com.example.finance.repository.FacultyRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;
    private final FacultyRepository facultyRepository;
    private final DisciplineMapper disciplineMapper;

    public List<DisciplineDTO> findAll() {
        return disciplineRepository.findAll().stream()
                .map(disciplineMapper::toDto)
                .collect(Collectors.toList());
    }

    public DisciplineDTO findById(Long id) {
        Optional<Discipline> discipline = disciplineRepository.findById(id);
        return discipline.map(disciplineMapper::toDto).orElse(null);
    }

    public DisciplineDTO create(DisciplineDTO disciplineDTO) {
        Faculty faculty = facultyRepository.findById(disciplineDTO.getFacultyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO, faculty);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    public DisciplineDTO update(Long id, DisciplineDTO disciplineDTO) {
        if (!disciplineRepository.existsById(id)) {
            return null;
        }
        Faculty faculty = facultyRepository.findById(disciplineDTO.getFacultyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO, faculty);
        discipline.setId(id);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    public void delete(Long id) {
        if (disciplineRepository.existsById(id)) {
            disciplineRepository.deleteById(id);
        }
    }
}
