package br.com.jjdev.todolist.dto;

public record CustomExceptionDTO(
        String message,
        int statusCode
) {
}
