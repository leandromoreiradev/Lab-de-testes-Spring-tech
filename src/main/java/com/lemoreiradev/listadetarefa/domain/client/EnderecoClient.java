package com.lemoreiradev.listadetarefa.domain.client;

import com.lemoreiradev.listadetarefa.domain.config.FeignConfig;
import com.lemoreiradev.listadetarefa.domain.dto.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${webservice.via-cep.name}", url = "${webservice.via-cep.url}", configuration = FeignConfig.class)
public interface EnderecoClient {
    @GetMapping(value = "/{cep}/json/")
    EnderecoDTO buscaEndereco(@PathVariable String cep);
}
