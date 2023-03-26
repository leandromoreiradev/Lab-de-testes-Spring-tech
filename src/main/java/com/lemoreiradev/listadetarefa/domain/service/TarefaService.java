package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.client.EnderecoClient;
import com.lemoreiradev.listadetarefa.domain.config.properties.FeignConfigProperties;
import com.lemoreiradev.listadetarefa.domain.dto.EnderecoDTO;
import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.dto.TarefaDTO;
import com.lemoreiradev.listadetarefa.domain.exceptions.PessoaNaoEncontradaException;
import com.lemoreiradev.listadetarefa.domain.exceptions.TarefaNaoEncontrada;
import com.lemoreiradev.listadetarefa.domain.mapper.PessoaMapper;
import com.lemoreiradev.listadetarefa.domain.mapper.TarefaMapper;
import com.lemoreiradev.listadetarefa.domain.model.Tarefa;
import com.lemoreiradev.listadetarefa.domain.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final PessoaService pessoaService;
    private final EnderecoClient enderecoClient;
    private final FeignConfigProperties feignConfigProperties;

    @Transactional
    public TarefaDTO criar(TarefaDTO tarefaDTO, Long id, String cep) {
        PessoaDTO pessoa = pessoaService.buscarPessoa(id);
        log.info("Buscando endereço no service: {}, na url: {}", feignConfigProperties.getName(),
                String.format("%s/%s/json/", feignConfigProperties.getUrl(), cep));
        EnderecoDTO enderecoDTO = enderecoClient.buscaEndereco(cep);
        if(Objects.isNull(pessoa)) {
            throw new PessoaNaoEncontradaException("Não encontrado");
        }
        tarefaDTO.setEndereco(enderecoDTO);
        tarefaDTO.setPessoa(PessoaMapper.toModel(pessoa));
        return TarefaMapper.toDTO(tarefaRepository.save(TarefaMapper.toModel(tarefaDTO)));
    }

    @Transactional
    public Tarefa atualizar(Tarefa tarefa, Long id) {
        try {
            tarefa = tarefaRepository.findById(id).get();
            tarefa = Tarefa.builder()
                    .nome(tarefa.getNome())
                    .endereco(tarefa.getEndereco())
                    .pessoa(tarefa.getPessoa())
                    .isCompleta(tarefa.getIsCompleta())
                    .build();
            return  tarefaRepository.save(tarefa);

        } catch (EntityNotFoundException e) {
            throw new TarefaNaoEncontrada("Não encontrado");
        }
    }

    @Transactional
    public void excluir(Long id) {
        try {
            Tarefa tarefa = tarefaRepository.findById(id).get();
            tarefaRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new TarefaNaoEncontrada("Não encontrado");
        }
    }

    public List<Tarefa> buscarTarefasPorPessoa(Long id) {
        PessoaDTO pessoa = pessoaService.buscarPessoa(id);
        if(Objects.isNull(pessoa)) {
            throw new PessoaNaoEncontradaException("Não encontrada");
        }
        return tarefaRepository.findByPessoaId(id);
    }
}
