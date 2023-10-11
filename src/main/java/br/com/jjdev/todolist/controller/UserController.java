package br.com.jjdev.todolist.controller;

import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.domain.user.UserType;
import br.com.jjdev.todolist.dto.UpdateUserDTO;
import br.com.jjdev.todolist.dto.UserDTO;
import br.com.jjdev.todolist.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDTO userDTO) throws Exception {
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<User>> searchUsers(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserType userType,
            @PageableDefault() Pageable pageable
    ) throws Exception {
        return new ResponseEntity<>(userService.searchProducts(id, name, email, userType, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editUser(
        @PathVariable UUID id,
        @RequestBody @Valid UpdateUserDTO user
    ) throws Exception{
        userService.updateUser(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id
    ) throws Exception {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
