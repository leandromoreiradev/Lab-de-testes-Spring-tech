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

    //Aqui temos uma relação 1 -> n porem unidirecional pois não anotamos desse lado,
    //apenas do lado da Tarefa, caso anotassemos aqui seria bidirecional e ficaria assim:
    //@OneToMany(mapppedby = "pessoa")
    //****** private List<Tarefa> tarefas;
}
