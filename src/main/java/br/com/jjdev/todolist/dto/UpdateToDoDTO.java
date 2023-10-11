package br.com.jjdev.todolist.dto;

import br.com.jjdev.todolist.domain.todo.ToDoStatus;

import java.util.UUID;

public record UpdateToDoDTO(
        String name,
        String description,
        ToDoStatus status,
        UUID user_id
) {
}
