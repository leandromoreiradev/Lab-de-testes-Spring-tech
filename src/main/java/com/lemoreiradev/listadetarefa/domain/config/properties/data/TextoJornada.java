package com.lemoreiradev.listadetarefa.domain.config.properties.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class TextoJornada {

    private String titulo;
    private String subtitulo;

}
