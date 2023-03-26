package com.lemoreiradev.listadetarefa.domain.config.properties;

import com.lemoreiradev.listadetarefa.domain.config.properties.data.TestOBJConfig;
import com.lemoreiradev.listadetarefa.domain.config.properties.data.TextoJornada;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Slf4j
@Data
@ConfigurationProperties(prefix = "home")
//@ConfigurationProperties(prefix = "obj-test")
public class HomeProperties {

    List<TextoJornada> textos;

    private TestOBJConfig testOBJConfig;




}
