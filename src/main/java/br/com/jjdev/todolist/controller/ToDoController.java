package br.com.jjdev.todolist.controller;

import br.com.jjdev.todolist.domain.todo.ToDo;
import br.com.jjdev.todolist.dto.ToDoDTO;
import br.com.jjdev.todolist.dto.UpdateToDoDTO;
import br.com.jjdev.todolist.service.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @PostMapping("/")
    public ResponseEntity<Void> createToDo(@RequestBody @Valid ToDoDTO toDoDTO, HttpServletRequest request) throws Exception {

        toDoDTO.setUser_id(request.getAttribute("user_id").toString());

        toDoService.createToDo(toDoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<Page<ToDo>> searchToDo(
            @RequestParam(required = false) UUID id,
            @PageableDefault() Pageable pageable
    ) throws Exception {
        return new ResponseEntity<>(toDoService.searchTodos(id, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editToDo(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateToDoDTO todo
    ) throws Exception {
        toDoService.updateToDo(id, todo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(
            @PathVariable UUID id) throws Exception {
        toDoService.deleteToDo(id);

        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
