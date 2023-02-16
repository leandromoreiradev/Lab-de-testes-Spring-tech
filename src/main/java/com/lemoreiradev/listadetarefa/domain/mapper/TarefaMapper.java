package com.lemoreiradev.listadetarefa.domain.mapper;

import com.lemoreiradev.listadetarefa.domain.dto.TarefaDTO;
import com.lemoreiradev.listadetarefa.domain.model.Tarefa;

public class TarefaMapper {
    public static TarefaDTO toDTO(Tarefa tarefa) {
        return TarefaDTO.builder()
                .id(tarefa.getId())
                .nome(tarefa.getNome())
                .endereco(tarefa.getEndereco())
                .isCompleta(tarefa.getIsCompleta())
                .build();

    }

    public static Tarefa toModel(TarefaDTO tarefaDTO) {
        return Tarefa.builder()
                .id(tarefaDTO.getId())
                .nome(tarefaDTO.getNome())
                .endereco(tarefaDTO.getEndereco())
                .pessoa(tarefaDTO.getPessoa())
                .isCompleta(tarefaDTO.getIsCompleta())
                .build();

    }
}
