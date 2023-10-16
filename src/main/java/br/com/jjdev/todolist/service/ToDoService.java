package br.com.jjdev.todolist.service;

import br.com.jjdev.todolist.domain.todo.ToDo;
import br.com.jjdev.todolist.domain.todo.ToDoStatus;
import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.dto.ToDoDTO;
import br.com.jjdev.todolist.dto.UpdateToDoDTO;
import br.com.jjdev.todolist.repository.ToDoRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository todoRepository;
    private final UserService userService;
    private final EntityManager entityManager;

    public void createToDo(ToDoDTO toDo) throws Exception {

        User user = userService.getUserById(UUID.fromString(toDo.getUser_id()));

        if (user == null) {
            throw new Exception("User not found!");
        }

        todoRepository.save(ToDo.builder()
                .name(toDo.getName())
                .description(toDo.getDescription())
                .status(ToDoStatus.NOT_STARTED)
                .user(user)
                .build());
    }

    public ToDo getToDoById(UUID id) throws Exception {
        return this.todoRepository.findById(id).orElseThrow(() -> new Exception("Could not find todo"));
    }

    public Page<ToDo> searchTodos(UUID id, Pageable pageable) throws Exception {
        Page<ToDo> todos = this.todoRepository.findAll(pageable);

        return todos;
    }

    public void deleteToDo(UUID id) throws Exception {
        this.todoRepository.deleteById(getToDoById(id).getId());
    }

    public void updateToDo(UUID id, UpdateToDoDTO todo) throws Exception {
        ToDo editedToDo = this.getToDoById(id);

        updateSelected(editedToDo, todo);

        this.todoRepository.save(editedToDo);
    }

    private void updateSelected(ToDo editedToDo, UpdateToDoDTO todo){
        if (todo.name() != null) {
            editedToDo.setName(todo.name());
        }

        if (todo.description() != null) {
            editedToDo.setDescription(todo.description());
        }

        if (todo.status() != null) {
            editedToDo.setStatus(todo.status());
        }
    }


}
