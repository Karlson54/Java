package com.example.finance.mapper.discipline;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.discipline.DisciplineDTO;
import com.example.finance.model.Discipline;
import com.example.finance.model.Faculty;

@Component
public class DisciplineMapper {
    private final ModelMapper modelMapper;

    public DisciplineMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DisciplineDTO toDto(Discipline discipline) {
        DisciplineDTO disciplineDTO = modelMapper.map(discipline, DisciplineDTO.class);
        disciplineDTO.setFacultyId(discipline.getFaculty().getId());
        return disciplineDTO;
    }

    public Discipline toEntity(DisciplineDTO disciplineDTO, Faculty faculty) {
        Discipline discipline = modelMapper.map(disciplineDTO, Discipline.class);
        discipline.setFaculty(faculty);
        return discipline;
    }
}
