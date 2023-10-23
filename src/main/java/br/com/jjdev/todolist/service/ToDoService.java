package br.com.jjdev.todolist.service;

import br.com.jjdev.todolist.domain.todo.ToDo;
import br.com.jjdev.todolist.domain.todo.ToDoStatus;
import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.dto.ToDoDTO;
import br.com.jjdev.todolist.dto.UpdateToDoDTO;
import br.com.jjdev.todolist.repository.ToDoRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public Page<ToDo> searchTodos(UUID id, Pageable pageable, HttpServletRequest request,  Authentication auth) throws Exception {

        List<ToDo> helper = this.userService.getUserById(UUID.fromString(request.getAttribute("user_id").toString())).getToDos();

        return new PageImpl<>(helper, pageable, helper.size());
    }

    public void deleteToDo(UUID id, HttpServletRequest request, Authentication auth) throws Exception {

        User userDetails = (User) auth.getPrincipal();

        if((!(Objects.equals(request.getAttribute("role").toString(), "ADMIN"))) && !(userDetails.getId().equals(this.getToDoById(id).getUser().getId()))) {
            throw new Exception("Acesso negado: Você não tem permissão para mexer esse ToDo.");
        }

        this.todoRepository.deleteById(getToDoById(id).getId());
    }

    public void updateToDo(UUID id, UpdateToDoDTO todo, HttpServletRequest request, Authentication auth) throws Exception {
        User userDetails = (User) auth.getPrincipal();

        if((!(Objects.equals(request.getAttribute("role").toString(), "ADMIN"))) && !(userDetails.getId().equals(this.getToDoById(id).getUser().getId()))) {
            throw new Exception("Acesso negado: Você não tem permissão para mexer esse ToDo.");
        }

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
