package br.com.jjdev.todolist.dto;

import br.com.jjdev.todolist.domain.todo.ToDoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record ToDoDTO(

        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        String description,
        @NotBlank
        @NotNull
        @UUID
        String user_id
) {
}
