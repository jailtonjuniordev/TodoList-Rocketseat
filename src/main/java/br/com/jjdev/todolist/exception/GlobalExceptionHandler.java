package br.com.jjdev.todolist.exception;


import br.com.jjdev.todolist.dto.CustomExceptionDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionDTO> genericExceptionHandler(Exception ex) {
        CustomExceptionDTO exResponse = new CustomExceptionDTO(ex.getMessage(), 500);
        return new ResponseEntity<>(exResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomExceptionDTO> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        CustomExceptionDTO exResponse= new CustomExceptionDTO(ex.getMessage(), 400);
        return new ResponseEntity<>(exResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        CustomExceptionDTO exResponse= new CustomExceptionDTO(ex.getMessage(), 400);
        return new ResponseEntity<>(exResponse, HttpStatus.BAD_REQUEST);
    }
}
