package br.com.jjdev.todolist.controller;

import br.com.jjdev.todolist.dto.ToDoDTO;
import br.com.jjdev.todolist.service.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @PostMapping("/")
    public ResponseEntity<Void> createToDo(@RequestBody @Valid ToDoDTO toDoDTO) throws Exception {
        toDoService.createToDo(toDoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
