package com.lemoreiradev.listadetarefa.domain.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    //Tratando validações do bena validation
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        List<ObjectError> erros = ex.getBindingResult().getAllErrors();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            message.append(error.getDefaultMessage()).append("\n");
        }

        return this.handleExceptionInternal(ex, message.toString(), headers, status, request);
    }

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
