package com.example.finance.mapper.group;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.group.GroupDTO;
import com.example.finance.model.Faculty;
import com.example.finance.model.Group;

@Component
public class GroupMapper {
    private final ModelMapper modelMapper;

    public GroupMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupDTO toDto(Group group) {
        GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
        groupDTO.setFacultyId(group.getFaculty().getId());
        return groupDTO;
    }

    public Group toEntity(GroupDTO groupDTO, Faculty faculty) {
        Group group = modelMapper.map(groupDTO, Group.class);
        group.setFaculty(faculty);
        return group;
    }
}
