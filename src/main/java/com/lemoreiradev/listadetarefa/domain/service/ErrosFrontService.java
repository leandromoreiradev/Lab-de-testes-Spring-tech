package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.config.properties.ErrosFrontProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ErrosFrontService {

    private final ErrosFrontProperties errosFrontProperties;

    public List<Map<String, String>> getErrosFront() {
        return errosFrontProperties.getErros();
    }

}
