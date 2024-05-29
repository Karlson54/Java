package com.example.finance.mapper.mark;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.mark.MarkDTO;
import com.example.finance.model.Control;
import com.example.finance.model.Mark;
import com.example.finance.model.StudentProfile;

@Component
public class MarkMapper {
    private final ModelMapper modelMapper;

    public MarkMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.createTypeMap(Mark.class, MarkDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getControl().getId(), MarkDTO::setControlId);
                    mapper.map(src -> src.getStudent().getId(), MarkDTO::setStudentId);
                });
    }

    public MarkDTO toDto(Mark mark) {
        return modelMapper.map(mark, MarkDTO.class);
    }

    public Mark toEntity(MarkDTO markDTO, Control control, StudentProfile student) {
        Mark mark = modelMapper.map(markDTO, Mark.class);
        mark.setControl(control);
        mark.setStudent(student);
        return mark;
    }
}
