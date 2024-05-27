package com.example.finance.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.finance.dto.user.UserDTO;
import com.example.finance.mapper.user.UserMapper;
import com.example.finance.model.User;
import com.example.finance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDto).orElse(null);
    }

    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            return null;
        }
        User user = userMapper.toEntity(userDTO);
        user.setId(id);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
