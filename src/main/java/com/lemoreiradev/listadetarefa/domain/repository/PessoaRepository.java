package com.lemoreiradev.listadetarefa.domain.repository;

import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByCpf (String cpf);
}
