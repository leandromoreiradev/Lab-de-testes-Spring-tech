package com.lemoreiradev.listadetarefa.domain.controller;

import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.dto.TarefaDTO;
import com.lemoreiradev.listadetarefa.domain.mapper.TarefaMapper;
import com.lemoreiradev.listadetarefa.domain.model.Contato;
import com.lemoreiradev.listadetarefa.domain.model.Tarefa;
import com.lemoreiradev.listadetarefa.domain.service.PessoaService;
import com.lemoreiradev.listadetarefa.domain.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;
    private final TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listar() {
        List<PessoaDTO> pessoas = pessoaService.listar();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPessoa(@PathVariable Long id) {
        PessoaDTO pessoa = pessoaService.buscarPessoa(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<PessoaDTO> buscarPessoaPorNome(@RequestParam String nome) {
        PessoaDTO pessoa = pessoaService.buscarPessoaPorNome(nome);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/{id}/tarefas")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasPorPessoa(@PathVariable Long id) {
        List<Tarefa> tarefas = tarefaService.buscarTarefasPorPessoa(id);
        return ResponseEntity.ok(tarefas.stream().map(TarefaMapper::toDTO).collect(Collectors.toList()));
    }

    @PostMapping("/{id}/contatos")
    public ResponseEntity<Contato> criarContato(@RequestBody Contato contato, @PathVariable Long id) {
        contato = pessoaService.criarContato(contato, id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contato.getId())
                .toUri();
        return ResponseEntity.created(uri).body(contato);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        pessoaDTO = pessoaService.criarPessoa(pessoaDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pessoaDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pessoaDTO);
    }

    @PostMapping("/{id}/{cep}/tarefas")
    public ResponseEntity<TarefaDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO, @PathVariable Long id, @PathVariable String cep) {
        tarefaDTO = tarefaService.criar(tarefaDTO, id, cep);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarefaDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(tarefaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@RequestBody PessoaDTO pessoaDTO, @PathVariable Long id) {
        pessoaDTO = pessoaService.atualizar(pessoaDTO, id);
        return ResponseEntity.ok(pessoaDTO) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pessoaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
