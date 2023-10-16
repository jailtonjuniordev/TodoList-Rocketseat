package br.com.jjdev.todolist.controller;

import br.com.jjdev.todolist.domain.user.User;
import br.com.jjdev.todolist.dto.AuthResponseDTO;
import br.com.jjdev.todolist.dto.AuthenticationDTO;
import br.com.jjdev.todolist.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO credentials) throws Exception {
        var emailPass = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var auth = this.authenticationManager.authenticate(emailPass);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateJWTToken(user);
        AuthResponseDTO response = new AuthResponseDTO(token, user.getEmail(), user.getName(), user.getUserType());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
