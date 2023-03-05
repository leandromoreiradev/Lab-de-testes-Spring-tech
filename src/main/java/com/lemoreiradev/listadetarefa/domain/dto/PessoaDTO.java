package com.lemoreiradev.listadetarefa.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lemoreiradev.listadetarefa.domain.model.Contato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "{name.not.blank}") //Não pode ser em branco, nem vazio, nem espaço em branco
    @Size(min = 3, message = "{min.character.invalid}")
    private String nome;
    @NotBlank(message = "{cpf.not.blank}")
    private String cpf;
    private Contato contato;
    @NotBlank(message = "{password.not.blank}")
    @Pattern(regexp = "^(?=.*\\d).{4,8}$", message = "{usuario.password.invalid}")
    private String senha;
    private List<TarefaDTO> tarefas;
}
