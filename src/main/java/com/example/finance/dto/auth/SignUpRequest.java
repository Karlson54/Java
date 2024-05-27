package com.example.finance.dto.auth;


import com.example.finance.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Registration Request")
@NoArgsConstructor
public class SignUpRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 2, max = 50, message = "Username must be between 5 and 50 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;


    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(max = 255, message = "Password length should not exceed 255 characters")
    private String password;

    @Schema(description = "User Role", example = "ROLE_USER")
    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    private Role role;
}