package com.lemoreiradev.listadetarefa.domain.config;

import com.lemoreiradev.listadetarefa.domain.config.properties.ConfigProperties;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@EnableConfigurationProperties injeta a classe de config value = ConfigProperties.class no boot da app
// por conta da anotação @Configuration
@Configuration
@EnableConfigurationProperties(value = ConfigProperties.class)
public class FeignConfig {

    private static final String CANAL = "web";

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor () {
        return requestTemplate -> {
            requestTemplate.header("CANAL", CANAL);
        };
    }
}
