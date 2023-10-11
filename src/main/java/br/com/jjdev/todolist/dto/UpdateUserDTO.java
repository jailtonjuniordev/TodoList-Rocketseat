package br.com.jjdev.todolist.dto;

import br.com.jjdev.todolist.domain.user.UserType;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDTO(
        String name,

        @Email
        String email,

        @Length(min = 8, message = "Password must be at least 8 characters")

        String password,

        UserType userType

) {
}
