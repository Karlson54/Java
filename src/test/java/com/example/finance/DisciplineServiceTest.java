package com.example.finance;

import com.example.finance.dto.discipline.DisciplineDTO;
import com.example.finance.mapper.discipline.DisciplineMapper;
import com.example.finance.model.Discipline;
import com.example.finance.model.Faculty;
import com.example.finance.repository.DisciplineRepository;
import com.example.finance.repository.FacultyRepository;
import com.example.finance.service.discipline.DisciplineService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisciplineServiceTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private DisciplineMapper disciplineMapper;

    @InjectMocks
    private DisciplineService disciplineService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Discipline discipline = new Discipline();
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        when(disciplineRepository.findAll()).thenReturn(List.of(discipline));
        when(disciplineMapper.toDto(discipline)).thenReturn(disciplineDTO);

        List<DisciplineDTO> result = disciplineService.findAll();

        assertEquals(1, result.size());
        assertEquals(disciplineDTO, result.get(0));
        verify(disciplineRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Long disciplineId = 1L;
        Discipline discipline = new Discipline();
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.of(discipline));
        when(disciplineMapper.toDto(discipline)).thenReturn(disciplineDTO);

        DisciplineDTO result = disciplineService.findById(disciplineId);

        assertEquals(disciplineDTO, result);
        verify(disciplineRepository, times(1)).findById(disciplineId);
    }

    @Test
    public void testFindById_NotFound() {
        Long disciplineId = 1L;
        when(disciplineRepository.findById(disciplineId)).thenReturn(Optional.empty());

        DisciplineDTO result = disciplineService.findById(disciplineId);

        assertNull(result);
        verify(disciplineRepository, times(1)).findById(disciplineId);
    }

    @Test
    public void testCreate() {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setFacultyId(1L);
        Faculty faculty = new Faculty();
        Discipline discipline = new Discipline();
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(disciplineMapper.toEntity(disciplineDTO, faculty)).thenReturn(discipline);
        when(disciplineRepository.save(discipline)).thenReturn(discipline);
        when(disciplineMapper.toDto(discipline)).thenReturn(disciplineDTO);

        DisciplineDTO result = disciplineService.create(disciplineDTO);

        assertEquals(disciplineDTO, result);
        verify(facultyRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(discipline);
    }

    @Test
    public void testUpdate() {
        Long disciplineId = 1L;
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setFacultyId(1L);
        Faculty faculty = new Faculty();
        Discipline discipline = new Discipline();
        when(disciplineRepository.existsById(disciplineId)).thenReturn(true);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(disciplineMapper.toEntity(disciplineDTO, faculty)).thenReturn(discipline);
        when(disciplineRepository.save(discipline)).thenReturn(discipline);
        when(disciplineMapper.toDto(discipline)).thenReturn(disciplineDTO);

        DisciplineDTO result = disciplineService.update(disciplineId, disciplineDTO);

        assertEquals(disciplineDTO, result);
        verify(disciplineRepository, times(1)).existsById(disciplineId);
        verify(facultyRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(discipline);
    }

    @Test
    public void testDelete() {
        Long disciplineId = 1L;
        when(disciplineRepository.existsById(disciplineId)).thenReturn(true);

        disciplineService.delete(disciplineId);

        verify(disciplineRepository, times(1)).existsById(disciplineId);
        verify(disciplineRepository, times(1)).deleteById(disciplineId);
    }
}
