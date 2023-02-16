package com.lemoreiradev.listadetarefa.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lemoreiradev.listadetarefa.domain.model.Contato;
import com.lemoreiradev.listadetarefa.domain.model.Tarefa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaDTO {

    private Long id;
    private String nome;
    private String cpf;
    private Contato contato;
    private String senha;
    private List<TarefaDTO> tarefas;
}
