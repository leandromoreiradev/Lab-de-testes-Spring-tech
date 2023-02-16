package com.lemoreiradev.listadetarefa.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarefaDTO {

    private Long id;
    private String nome;
    private Boolean isCompleta;
    private EnderecoDTO endereco;
    private Pessoa pessoa;

}
