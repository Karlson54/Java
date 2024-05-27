package com.example.finance.mapper.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.finance.dto.user.UserDTO;
import com.example.finance.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
