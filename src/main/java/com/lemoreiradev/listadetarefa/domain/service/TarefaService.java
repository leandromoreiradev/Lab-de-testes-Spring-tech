package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.client.EnderecoClient;
import com.lemoreiradev.listadetarefa.domain.dto.EnderecoDTO;
import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.dto.TarefaDTO;
import com.lemoreiradev.listadetarefa.domain.exceptions.PessoaNaoEncontradaException;
import com.lemoreiradev.listadetarefa.domain.exceptions.TarefaNaoEncontrada;
import com.lemoreiradev.listadetarefa.domain.mapper.PessoaMapper;
import com.lemoreiradev.listadetarefa.domain.mapper.TarefaMapper;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import com.lemoreiradev.listadetarefa.domain.model.Tarefa;
import com.lemoreiradev.listadetarefa.domain.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final PessoaService pessoaService;
    private final EnderecoClient enderecoClient;

    public TarefaDTO criar(TarefaDTO tarefaDTO, Long id, String cep) {
        PessoaDTO pessoa = pessoaService.buscarPessoa(id);
        EnderecoDTO enderecoDTO = enderecoClient.buscaEndereco(cep);
        if(Objects.isNull(pessoa)) {
            throw new PessoaNaoEncontradaException("Não encontrado");
        }
        tarefaDTO.setEndereco(enderecoDTO);
        tarefaDTO.setPessoa(PessoaMapper.toModel(pessoa));
        return TarefaMapper.toDTO(tarefaRepository.save(TarefaMapper.toModel(tarefaDTO)));
    }
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

        } catch (Exception e) {
            throw new TarefaNaoEncontrada("Não encontrado");
        }
    }

    public void excluir(Long id) {
        try {
            Tarefa tarefa = tarefaRepository.findById(id).get();
            tarefaRepository.deleteById(id);
        } catch (Exception e) {
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
