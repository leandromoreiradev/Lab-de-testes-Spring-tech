package com.lemoreiradev.listadetarefa.domain.client;

import com.lemoreiradev.listadetarefa.domain.dto.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "https://viacep.com.br/ws")
public interface EnderecoClient {
    @GetMapping(value = "/{cep}/json/")
    EnderecoDTO buscaEndereco(@PathVariable String cep);
}
