package com.lemoreiradev.listadetarefa.domain.controller.v1;

import com.lemoreiradev.listadetarefa.domain.config.properties.data.TextoJornada;
import com.lemoreiradev.listadetarefa.domain.service.TextoHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/home")
public class HomeController {

    private final TextoHomeService textoHomeService;

    @GetMapping
    public ResponseEntity<List<TextoJornada>> listarTextosDaJornadaHome() {
        return ResponseEntity.ok(textoHomeService.listarTextosDaJornadaHome());
    }


    @GetMapping("/teste")
    public List<String> teste() {
        return textoHomeService.teste();
    }
}
