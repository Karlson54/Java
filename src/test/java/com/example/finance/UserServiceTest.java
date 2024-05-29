package com.example.finance;

import com.example.finance.dto.user.UserDTO;
import com.example.finance.mapper.user.UserMapper;
import com.example.finance.model.User;
import com.example.finance.repository.UserRepository;
import com.example.finance.service.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<User> users = List.of(new User(), new User());
        List<UserDTO> userDTOs = List.of(new UserDTO(), new UserDTO());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTOs.get(0), userDTOs.get(1));

        List<UserDTO> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    public void testFindById() {
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = userService.findById(1L);

        assertEquals(userDTO, result);
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    public void testFindById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserDTO result = userService.findById(1L);

        assertNull(result);
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(0)).toDto(any(User.class));
    }

    @Test
    public void testCreate() {
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = userService.create(userDTO);

        assertEquals(userDTO, result);
        verify(userMapper, times(1)).toEntity(userDTO);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userRepository.existsById(1L)).thenReturn(true);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = userService.update(1L, userDTO);

        assertEquals(userDTO, result);
        verify(userRepository, times(1)).existsById(1L);
        verify(userMapper, times(1)).toEntity(userDTO);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    public void testUpdate_NotFound() {
        UserDTO userDTO = new UserDTO();

        when(userRepository.existsById(1L)).thenReturn(false);

        UserDTO result = userService.update(1L, userDTO);

        assertNull(result);
        verify(userRepository, times(1)).existsById(1L);
        verify(userMapper, times(0)).toEntity(any(UserDTO.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testDelete() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.delete(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        userService.delete(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testGetByUsername() {
        User user = new User();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        User result = userService.getByUsername("user");

        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername("user");
    }

    @Test
    public void testGetByUsername_NotFound() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getByUsername("user");
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("user");
    }

    @Test
    public void testUserDetailsService() {
        UserDetailsService userDetailsService = userService.userDetailsService();

        assertNotNull(userDetailsService);
    }
}
