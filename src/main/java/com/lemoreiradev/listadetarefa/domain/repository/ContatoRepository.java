package com.lemoreiradev.listadetarefa.domain.repository;

import com.lemoreiradev.listadetarefa.domain.model.Contato;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
