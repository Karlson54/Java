package com.example.finance.mapper.control;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.control.ControlDTO;
import com.example.finance.model.Control;
import com.example.finance.model.Discipline;
import com.example.finance.model.Group;

@Component
public class ControlMapper {
    private final ModelMapper modelMapper;

    public ControlMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Control.class, ControlDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getDiscipline().getId(), ControlDTO::setDisciplineId);
                    mapper.map(src -> src.getGroup().getId(), ControlDTO::setGroupId);
                });

    }

    public ControlDTO toDto(Control control) {
        return modelMapper.map(control, ControlDTO.class);
    }

    public Control toEntity(ControlDTO controlDTO, Discipline discipline, Group group) {
        Control control = modelMapper.map(controlDTO, Control.class);
        control.setDiscipline(discipline);
        control.setGroup(group);
        return control;
    }
}
