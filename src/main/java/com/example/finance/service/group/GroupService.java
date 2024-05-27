package com.example.finance.service.group;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.dto.group.GroupDTO;
import com.example.finance.mapper.group.GroupMapper;
import com.example.finance.model.Faculty;
import com.example.finance.model.Group;
import com.example.finance.repository.FacultyRepository;
import com.example.finance.repository.GroupRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, FacultyRepository facultyRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupDTO> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    public GroupDTO findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(groupMapper::toDto).orElse(null);
    }

    public GroupDTO create(GroupDTO groupDTO) {
        Faculty faculty = facultyRepository.findById(groupDTO.getFacultyId()).orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));
        Group group = groupMapper.toEntity(groupDTO, faculty);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    public GroupDTO update(Long id, GroupDTO groupDTO) {
        if (!groupRepository.existsById(id)) {
            return null;
        }
        Faculty faculty = facultyRepository.findById(groupDTO.getFacultyId()).orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID"));
        Group group = groupMapper.toEntity(groupDTO, faculty);
        group.setId(id);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    public void delete(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
        }
    }
}
