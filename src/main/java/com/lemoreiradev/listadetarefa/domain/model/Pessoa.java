package com.lemoreiradev.listadetarefa.domain.model;

import com.lemoreiradev.listadetarefa.domain.dto.TarefaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Pessoa")
public class Pessoa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String senha;

    @OneToOne(mappedBy = "pessoa")
    private Contato contato; //Relacionamento um para um bidirecional pois mapeamos aqui com o  @OneToOne(mappedBy = "pessoa")

    //Relação bidirecional com tarefa
    @OneToMany(mappedBy = "pessoa")
    private List<Tarefa> tarefas;
}
