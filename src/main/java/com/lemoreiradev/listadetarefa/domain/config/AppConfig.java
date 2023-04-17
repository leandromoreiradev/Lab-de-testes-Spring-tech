package com.lemoreiradev.listadetarefa.domain.config;

import com.lemoreiradev.listadetarefa.domain.config.properties.ErrosFrontProperties;
import com.lemoreiradev.listadetarefa.domain.config.properties.HomeProperties;
import com.lemoreiradev.listadetarefa.domain.config.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableConfigurationProperties({HomeProperties.class, RedisProperties.class, ErrosFrontProperties.class})
public class AppConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
