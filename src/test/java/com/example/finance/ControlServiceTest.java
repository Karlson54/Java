package com.example.finance;

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
import com.example.finance.service.control.ControlService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControlServiceTest {

    @Mock
    private ControlRepository controlRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MarkRepository markRepository;

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private ControlMapper controlMapper;

    @Mock
    private MarkMapper markMapper;

    @InjectMocks
    private ControlService controlService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllControls() {
        Control control = new Control();
        ControlDTO controlDTO = new ControlDTO();
        when(controlRepository.findAll()).thenReturn(List.of(control));
        when(controlMapper.toDto(control)).thenReturn(controlDTO);

        List<ControlDTO> result = controlService.findAllControls();

        assertEquals(1, result.size());
        assertEquals(controlDTO, result.get(0));
        verify(controlRepository, times(1)).findAll();
    }

    @Test
    public void testFindControlById() {
        Long controlId = 1L;
        Control control = new Control();
        ControlDTO controlDTO = new ControlDTO();
        when(controlRepository.findById(controlId)).thenReturn(Optional.of(control));
        when(controlMapper.toDto(control)).thenReturn(controlDTO);

        ControlDTO result = controlService.findControlById(controlId);

        assertEquals(controlDTO, result);
        verify(controlRepository, times(1)).findById(controlId);
    }

    @Test
    public void testFindControlById_NotFound() {
        Long controlId = 1L;
        when(controlRepository.findById(controlId)).thenReturn(Optional.empty());

        ControlDTO result = controlService.findControlById(controlId);

        assertNull(result);
        verify(controlRepository, times(1)).findById(controlId);
    }

    @Test
    public void testCreateControl() {
        ControlDTO controlDTO = new ControlDTO();
        controlDTO.setDisciplineId(1L);
        controlDTO.setGroupId(1L);
        Discipline discipline = new Discipline();
        Group group = new Group();
        Control control = new Control();
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(controlMapper.toEntity(controlDTO, discipline, group)).thenReturn(control);
        when(controlRepository.save(control)).thenReturn(control);
        when(controlMapper.toDto(control)).thenReturn(controlDTO);

        ControlDTO result = controlService.createControl(controlDTO);

        assertEquals(controlDTO, result);
        verify(disciplineRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).findById(1L);
        verify(controlRepository, times(1)).save(control);
    }

    @Test
    public void testUpdateControl() {
        Long controlId = 1L;
        ControlDTO controlDTO = new ControlDTO();
        controlDTO.setDisciplineId(1L);
        controlDTO.setGroupId(1L);
        Discipline discipline = new Discipline();
        Group group = new Group();
        Control control = new Control();
        when(controlRepository.existsById(controlId)).thenReturn(true);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(controlMapper.toEntity(controlDTO, discipline, group)).thenReturn(control);
        when(controlRepository.save(control)).thenReturn(control);
        when(controlMapper.toDto(control)).thenReturn(controlDTO);

        ControlDTO result = controlService.updateControl(controlId, controlDTO);

        assertEquals(controlDTO, result);
        verify(controlRepository, times(1)).existsById(controlId);
        verify(disciplineRepository, times(1)).findById(1L);
        verify(groupRepository, times(1)).findById(1L);
        verify(controlRepository, times(1)).save(control);
    }

    @Test
    public void testDeleteControl() {
        Long controlId = 1L;
        when(controlRepository.existsById(controlId)).thenReturn(true);

        controlService.deleteControl(controlId);

        verify(controlRepository, times(1)).existsById(controlId);
        verify(controlRepository, times(1)).deleteById(controlId);
    }

    @Test
    public void testCreateMark() {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setControlId(1L);
        markDTO.setStudentId(1L);
        Control control = new Control();
        StudentProfile student = new StudentProfile();
        Mark mark = new Mark();
        when(controlRepository.findById(1L)).thenReturn(Optional.of(control));
        when(studentProfileRepository.findById(1L)).thenReturn(Optional.of(student));
        when(markMapper.toEntity(markDTO, control, student)).thenReturn(mark);
        when(markRepository.save(mark)).thenReturn(mark);
        when(markMapper.toDto(mark)).thenReturn(markDTO);

        MarkDTO result = controlService.createMark(markDTO);

        assertEquals(markDTO, result);
        verify(controlRepository, times(1)).findById(1L);
        verify(studentProfileRepository, times(1)).findById(1L);
        verify(markRepository, times(1)).save(mark);
    }

    @Test
    public void testFindMarksByStudentAndDiscipline() {
        Long studentId = 1L;
        Long disciplineId = 1L;
        Mark mark = new Mark();
        MarkDTO markDTO = new MarkDTO();
        when(markRepository.findAllByStudentIdAndControlDisciplineId(studentId, disciplineId)).thenReturn(List.of(mark));
        when(markMapper.toDto(mark)).thenReturn(markDTO);

        List<MarkDTO> result = controlService.findMarksByStudentAndDiscipline(studentId, disciplineId);

        assertEquals(1, result.size());
        assertEquals(markDTO, result.get(0));
        verify(markRepository, times(1)).findAllByStudentIdAndControlDisciplineId(studentId, disciplineId);
    }

    @Test
    public void testFindControlsByGroupId() {
        Long groupId = 1L;
        Control control = new Control();
        ControlDTO controlDTO = new ControlDTO();
        when(controlRepository.findAllByGroupId(groupId)).thenReturn(List.of(control));
        when(controlMapper.toDto(control)).thenReturn(controlDTO);

        List<ControlDTO> result = controlService.findControlsByGroupId(groupId);

        assertEquals(1, result.size());
        assertEquals(controlDTO, result.get(0));
        verify(controlRepository, times(1)).findAllByGroupId(groupId);
    }
}
