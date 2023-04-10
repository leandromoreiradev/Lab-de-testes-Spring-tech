package com.lemoreiradev.listadetarefa.domain.model;

import com.lemoreiradev.listadetarefa.domain.dto.EnderecoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean isCompleta;
    @Embedded
    private EnderecoDTO endereco;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa; //relacionamento muitos para um, unidirecional
    //no mapeamento n -> 1 ou 1 -> n sempre começar do lado do n -> 1 e add a FK chave estrangeira,
    //que é o valor da chave primaria em Pessoa

}
