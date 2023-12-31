package br.com.jjdev.todolist.domain.todo;


import br.com.jjdev.todolist.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "todo")
public class ToDo{


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ToDoStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName="id")
    @JsonIgnoreProperties("toDos")
    private User user;
}
