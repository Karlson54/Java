package com.example.finance.service.faculty;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.dto.faculty.FacultyDTO;
import com.example.finance.mapper.faculty.FacultyMapper;
import com.example.finance.model.Faculty;
import com.example.finance.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

    public List<FacultyDTO> findAll() {
        return facultyRepository.findAll().stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDTO findById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        return faculty.map(facultyMapper::toDto).orElse(null);
    }

    public FacultyDTO create(FacultyDTO facultyDTO) {
        Faculty faculty = facultyMapper.toEntity(facultyDTO);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(faculty);
    }

    public FacultyDTO update(Long id, FacultyDTO facultyDTO) {
        if (!facultyRepository.existsById(id)) {
            return null;
        }
        Faculty faculty = facultyMapper.toEntity(facultyDTO);
        faculty.setId(id);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(faculty);
    }

    public void delete(Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
        }
    }
}
