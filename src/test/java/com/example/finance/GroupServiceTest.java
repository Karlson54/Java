package com.example.finance;

import com.example.finance.dto.group.GroupDTO;
import com.example.finance.mapper.group.GroupMapper;
import com.example.finance.model.Faculty;
import com.example.finance.model.Group;
import com.example.finance.repository.FacultyRepository;
import com.example.finance.repository.GroupRepository;
import com.example.finance.service.group.GroupService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Group group = new Group();
        GroupDTO groupDTO = new GroupDTO();
        when(groupRepository.findAll()).thenReturn(List.of(group));
        when(groupMapper.toDto(group)).thenReturn(groupDTO);

        List<GroupDTO> result = groupService.findAll();

        assertEquals(1, result.size());
        assertEquals(groupDTO, result.get(0));
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Long groupId = 1L;
        Group group = new Group();
        GroupDTO groupDTO = new GroupDTO();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(groupMapper.toDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.findById(groupId);

        assertEquals(groupDTO, result);
        verify(groupRepository, times(1)).findById(groupId);
    }

    @Test
    public void testFindById_NotFound() {
        Long groupId = 1L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        GroupDTO result = groupService.findById(groupId);

        assertNull(result);
        verify(groupRepository, times(1)).findById(groupId);
    }

    @Test
    public void testCreate() {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setFacultyId(1L);
        Faculty faculty = new Faculty();
        Group group = new Group();
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(groupMapper.toEntity(groupDTO, faculty)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupMapper.toDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.create(groupDTO);

        assertEquals(groupDTO, result);
        verify(facultyRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    public void testUpdate() {
        Long groupId = 1L;
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setFacultyId(1L);
        Faculty faculty = new Faculty();
        Group group = new Group();
        when(groupRepository.existsById(groupId)).thenReturn(true);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(groupMapper.toEntity(groupDTO, faculty)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupMapper.toDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.update(groupId, groupDTO);

        assertEquals(groupDTO, result);
        verify(groupRepository, times(1)).existsById(groupId);
        verify(facultyRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    public void testDelete() {
        Long groupId = 1L;
        when(groupRepository.existsById(groupId)).thenReturn(true);

        groupService.delete(groupId);

        verify(groupRepository, times(1)).existsById(groupId);
        verify(groupRepository, times(1)).deleteById(groupId);
    }
}
