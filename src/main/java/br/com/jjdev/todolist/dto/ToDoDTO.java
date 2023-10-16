package br.com.jjdev.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDTO {
        @NotBlank
        @NotNull
        private String name;

        @NotBlank
        @NotNull
        private String description;

        private String user_id;
}
