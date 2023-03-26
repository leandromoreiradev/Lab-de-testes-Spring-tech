package com.lemoreiradev.listadetarefa.domain.config.properties;

import com.lemoreiradev.listadetarefa.domain.config.properties.data.TestOBJConfig;
import com.lemoreiradev.listadetarefa.domain.config.properties.data.TextoJornada;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Data
@ConfigurationProperties(prefix = "home") //home é o prefixo que antecede a prorpriedade textos la no application.yml
//@ConfigurationProperties(prefix = "obj-test") //no properties yml objTest equivale a obj-test no obj java tem que usar o "-"
public class HomeProperties {

    List<TextoJornada> textos;

    private TestOBJConfig testOBJConfig;




}
