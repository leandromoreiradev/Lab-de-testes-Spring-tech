package com.lemoreiradev.listadetarefa.domain.config.properties;

import com.lemoreiradev.listadetarefa.domain.config.properties.data.TextoJornada;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "home")
public class HomeProperties {

    List<TextoJornada> textos;

    private List<String> testes;


}
