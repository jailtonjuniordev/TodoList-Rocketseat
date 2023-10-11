package br.com.jjdev.todolist.service;

import br.com.jjdev.todolist.domain.todo.ToDo;
import br.com.jjdev.todolist.domain.todo.ToDoStatus;
import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.dto.ToDoDTO;
import br.com.jjdev.todolist.repository.ToDoRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository todoRepository;
    private final UserService userService;
    private final EntityManager entityManager;

    public void createToDo(ToDoDTO toDo) throws Exception {

        User user = userService.getUserById(UUID.fromString(toDo.user_id()));

        if (user == null) {
            throw new Exception("User not found!");
        }

        todoRepository.save(ToDo.builder()
                .name(toDo.name())
                .description(toDo.description())
                .status(ToDoStatus.NOT_STARTED)
                .user(user)
                .build());
    }

}
