package com.lemoreiradev.listadetarefa.domain.repository;

import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import com.lemoreiradev.listadetarefa.domain.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByPessoaId(Long id);

}
