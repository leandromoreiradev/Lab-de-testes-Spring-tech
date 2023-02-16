package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.exceptions.NegocioExeption;
import com.lemoreiradev.listadetarefa.domain.exceptions.PessoaNaoEncontradaException;
import com.lemoreiradev.listadetarefa.domain.mapper.PessoaMapper;
import com.lemoreiradev.listadetarefa.domain.mapper.TarefaMapper;
import com.lemoreiradev.listadetarefa.domain.model.Contato;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import com.lemoreiradev.listadetarefa.domain.repository.ContatoRepository;
import com.lemoreiradev.listadetarefa.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ContatoRepository contatoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<PessoaDTO> listar() {
        return pessoaRepository.findAll()
                .stream().map(PessoaMapper::toDTO).collect(Collectors.toList());
    }

    public PessoaDTO buscarPessoa(Long id){
        Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.findById(id)
                .orElseThrow(()-> new PessoaNaoEncontradaException("Não encontrado")));
        return PessoaMapper.toDTO(pessoa.get());

    }

    public PessoaDTO criar(PessoaDTO pessoaDTO) {
        Pessoa pessoa = Optional.ofNullable(pessoaRepository.findByCpf(pessoaDTO.getCpf()))
                .orElse(null);
        if(!Objects.isNull(pessoa)) {
            throw new NegocioExeption("Pessoa ja cadastrada com este cpf");
        }
        pessoa = PessoaMapper.toModel(pessoaDTO);
        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        return PessoaMapper.toDTO(pessoaRepository.save(pessoa));
    }

    public PessoaDTO atualizar(PessoaDTO pessoaDTO, Long id){
        try {
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);
            pessoa.get().setNome(pessoaDTO.getNome());
            pessoa.get().setCpf(pessoaDTO.getCpf());
            pessoa.get().setContato(pessoaDTO.getContato());
            pessoa.get().setSenha(passwordEncoder.encode(pessoaDTO.getSenha()));
            //pessoa.get().setTarefas(pessoaDTO.getTarefas().stream().map(tarefaDTO -> TarefaMapper.toModel(tarefaDTO)).collect(Collectors.toList()));

            return PessoaMapper.toDTO(pessoaRepository.save(pessoa.get()));

        } catch (Exception e) {
            throw new PessoaNaoEncontradaException("Não encontrado");
        }
    }

    public void excluir(Long id) {
        try {
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);
            pessoaRepository.deleteById(id);

        } catch (Exception e) {
            throw new PessoaNaoEncontradaException("Não encontrado");
        }
    }

    public Contato criarContato(Contato contato, Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(pessoa.isEmpty()) {
            throw new PessoaNaoEncontradaException("Não encontrada");
        }
        contato.setPessoa(pessoa.get());
        return contatoRepository.save(contato);
    }
}
