package br.com.jjdev.todolist.dto;

import jakarta.validation.constraints.Email;

public record AuthenticationDTO(
        @Email
        String email,
        String password
) {
}
