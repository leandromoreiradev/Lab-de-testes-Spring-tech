package com.lemoreiradev.listadetarefa.domain.controller.v1;

import com.lemoreiradev.listadetarefa.domain.config.properties.data.TestOBJConfig;
import com.lemoreiradev.listadetarefa.domain.config.properties.data.TextoJornada;

import com.lemoreiradev.listadetarefa.domain.service.ErrosFrontService;
import com.lemoreiradev.listadetarefa.domain.service.TextoHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/home")
public class HomeController {

    private final TextoHomeService textoHomeService;

    private final ErrosFrontService errosFrontService;

    @GetMapping(value = "/erros-front")
    public ResponseEntity<List<Map<String, String>>> getErrosFront() {
        return ResponseEntity.ok(errosFrontService.getErrosFront());
    }

    @GetMapping
    public ResponseEntity<List<TextoJornada>> listarTextosDaJornadaHome() {
        return ResponseEntity.ok(textoHomeService.listarTextosDaJornadaHome());
    }


    @GetMapping("/teste")
    public ResponseEntity<TestOBJConfig> teste() {
        return ResponseEntity.ok(textoHomeService.teste());
    }
}
