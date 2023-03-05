package com.lemoreiradev.listadetarefa.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NegocioExeption.class)
    public ResponseEntity<StandardError> negocioException(NegocioExeption e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("CPF já Cadastrado")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<StandardError> pessoaNaoEncontradaException(PessoaNaoEncontradaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Pessoa não cadastrada no sistema")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }

    @ExceptionHandler(TarefaNaoEncontrada.class)
    public ResponseEntity<StandardError> tarefaNaoEncontradaException(TarefaNaoEncontrada e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Tarefa não cadastrada no sistema")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build());
    }

}
