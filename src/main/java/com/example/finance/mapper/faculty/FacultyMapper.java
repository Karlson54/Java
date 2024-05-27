package com.example.finance.mapper.faculty;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.faculty.FacultyDTO;
import com.example.finance.model.Faculty;

@Component
public class FacultyMapper {
    private final ModelMapper modelMapper;

    public FacultyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FacultyDTO toDto(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDTO.class);
    }

    public Faculty toEntity(FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }
}
