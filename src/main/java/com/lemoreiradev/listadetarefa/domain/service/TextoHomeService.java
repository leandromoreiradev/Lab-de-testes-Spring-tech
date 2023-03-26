package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.config.properties.HomeProperties;
import com.lemoreiradev.listadetarefa.domain.config.properties.data.TextoJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextoHomeService {

    private final HomeProperties homeProperties;

    public List<TextoJornada> listarTextosDaJornadaHome() {
        return homeProperties.getTextos();
    }

    public List<String> teste() {
       return homeProperties.getTestes();
    }
}
