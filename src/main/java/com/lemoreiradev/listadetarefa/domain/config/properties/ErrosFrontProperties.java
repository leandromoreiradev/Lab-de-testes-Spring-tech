package com.lemoreiradev.listadetarefa.domain.config.properties;

import com.lemoreiradev.listadetarefa.domain.config.properties.data.TestOBJConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@Data
@ConfigurationProperties(prefix = "erros-front")
public class ErrosFrontProperties {

    List<Map<String, String>> erros;

}
