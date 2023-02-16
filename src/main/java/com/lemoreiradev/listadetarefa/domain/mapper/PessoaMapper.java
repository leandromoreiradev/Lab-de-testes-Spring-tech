package com.lemoreiradev.listadetarefa.domain.mapper;

import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PessoaMapper {

    public static Pessoa toModel(PessoaDTO pessoaDTO) {
        return Pessoa.builder()
                .id(pessoaDTO.getId())
                .nome(pessoaDTO.getNome())
                .cpf(pessoaDTO.getCpf())
                //.contato(pessoaDTO.getContato())
                .senha(pessoaDTO.getSenha())
                .build();
    }

    public static PessoaDTO toDTO(Pessoa pessoa) {
        return PessoaDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .cpf(pessoa.getCpf())
                .contato(pessoa.getContato())
//                .tarefas(Optional.ofNullable(pessoa.getTarefas())
//                        .map(tarefas -> tarefas.stream()
//                                .map(TarefaMapper::toDTO)
//                                .collect(Collectors.toList()))
//                        .orElse(null))
                .build();
    }
}
