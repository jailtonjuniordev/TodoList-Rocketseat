package br.com.jjdev.todolist.dto;

import br.com.jjdev.todolist.domain.user.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserDTO(
        @NotBlank(message = "Name cannot be Blank or Null")
        @NotNull(message = "Name cannot be Blank or Null")
        String name,

        @Email
        @NotNull(message = "Email cannot be Blank or Null")
        @NotBlank(message = "Email cannot be Blank or Null")
        String email,

        @NotNull(message = "Password cannot be Blank or Null")
        @NotBlank(message = "Password cannot be Blank or Null")
        @Length(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotNull(message = "UserType cannot be Blank or Null")
        UserType userType
) {
}
