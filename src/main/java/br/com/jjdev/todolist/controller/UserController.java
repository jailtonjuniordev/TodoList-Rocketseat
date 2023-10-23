package br.com.jjdev.todolist.controller;

import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.domain.user.UserType;
import br.com.jjdev.todolist.dto.UpdateUserDTO;
import br.com.jjdev.todolist.dto.UserDTO;
import br.com.jjdev.todolist.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @PageableDefault() Pageable pageable,
            HttpServletRequest request
    ) throws Exception {



        return new ResponseEntity<>(userService.searchUsers(id, name, email, userType, pageable, request
        ), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editUser(
        @PathVariable UUID id,
        @RequestBody @Valid UpdateUserDTO user,
        HttpServletRequest request
    ) throws Exception{
        userService.updateUser(id, user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            HttpServletRequest request
    ) throws Exception {
        userService.deleteUser(id, request);

        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
