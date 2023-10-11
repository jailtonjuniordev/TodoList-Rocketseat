package br.com.jjdev.todolist.repository;

import br.com.jjdev.todolist.domain.todo.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, UUID> {
}
