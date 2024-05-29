package com.example.finance;

import com.example.finance.dto.faculty.FacultyDTO;
import com.example.finance.mapper.faculty.FacultyMapper;
import com.example.finance.model.Faculty;
import com.example.finance.repository.FacultyRepository;
import com.example.finance.service.faculty.FacultyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private FacultyMapper facultyMapper;

    @InjectMocks
    private FacultyService facultyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Faculty faculty = new Faculty();
        FacultyDTO facultyDTO = new FacultyDTO();
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));
        when(facultyMapper.toDto(faculty)).thenReturn(facultyDTO);

        List<FacultyDTO> result = facultyService.findAll();

        assertEquals(1, result.size());
        assertEquals(facultyDTO, result.get(0));
        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Long facultyId = 1L;
        Faculty faculty = new Faculty();
        FacultyDTO facultyDTO = new FacultyDTO();
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty));
        when(facultyMapper.toDto(faculty)).thenReturn(facultyDTO);

        FacultyDTO result = facultyService.findById(facultyId);

        assertEquals(facultyDTO, result);
        verify(facultyRepository, times(1)).findById(facultyId);
    }

    @Test
    public void testFindById_NotFound() {
        Long facultyId = 1L;
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.empty());

        FacultyDTO result = facultyService.findById(facultyId);

        assertNull(result);
        verify(facultyRepository, times(1)).findById(facultyId);
    }

    @Test
    public void testCreate() {
        FacultyDTO facultyDTO = new FacultyDTO();
        Faculty faculty = new Faculty();
        when(facultyMapper.toEntity(facultyDTO)).thenReturn(faculty);
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyMapper.toDto(faculty)).thenReturn(facultyDTO);

        FacultyDTO result = facultyService.create(facultyDTO);

        assertEquals(facultyDTO, result);
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void testUpdate() {
        Long facultyId = 1L;
        FacultyDTO facultyDTO = new FacultyDTO();
        Faculty faculty = new Faculty();
        when(facultyRepository.existsById(facultyId)).thenReturn(true);
        when(facultyMapper.toEntity(facultyDTO)).thenReturn(faculty);
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyMapper.toDto(faculty)).thenReturn(facultyDTO);

        FacultyDTO result = facultyService.update(facultyId, facultyDTO);

        assertEquals(facultyDTO, result);
        verify(facultyRepository, times(1)).existsById(facultyId);
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void testDelete() {
        Long facultyId = 1L;
        when(facultyRepository.existsById(facultyId)).thenReturn(true);

        facultyService.delete(facultyId);

        verify(facultyRepository, times(1)).existsById(facultyId);
        verify(facultyRepository, times(1)).deleteById(facultyId);
    }
}
