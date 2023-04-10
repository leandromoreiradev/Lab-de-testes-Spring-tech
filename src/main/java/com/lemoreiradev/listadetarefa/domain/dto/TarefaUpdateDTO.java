package com.lemoreiradev.listadetarefa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaUpdateDTO {
    private Boolean isCompleta;
}
