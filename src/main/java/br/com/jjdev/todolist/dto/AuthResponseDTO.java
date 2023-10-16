package br.com.jjdev.todolist.dto;

import br.com.jjdev.todolist.domain.user.UserType;

public record AuthResponseDTO(
        String token,
        String email,
        String name,
        UserType role
) {
}
