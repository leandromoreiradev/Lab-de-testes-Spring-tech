package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.exceptions.NegocioExeption;
import com.lemoreiradev.listadetarefa.domain.exceptions.PessoaNaoEncontradaException;
import com.lemoreiradev.listadetarefa.domain.mapper.PessoaMapper;
import com.lemoreiradev.listadetarefa.domain.model.Contato;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import com.lemoreiradev.listadetarefa.domain.repository.ContatoRepository;
import com.lemoreiradev.listadetarefa.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .stream().map(PessoaMapper::toDTOresumido).collect(Collectors.toList());
    }


    public PessoaDTO buscarPessoa(Long id) {

        Optional<Pessoa> pessoa = Optional.ofNullable(pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Não encontrado")));
        return PessoaMapper.toDTOdetalhado(pessoa.get());

    }


    public PessoaDTO buscarPessoaPorNome(String nome) {
        Pessoa pessoa = pessoaRepository.findPessoaPorNomeIgnoreCaseContaining(nome);
        if (Objects.isNull(pessoa)) {
            throw new PessoaNaoEncontradaException("Não encontrado");
        }
        return PessoaMapper.toDTOdetalhado(pessoa);
    }


    @Transactional
    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = Optional.ofNullable(pessoaRepository.findByCpf(pessoaDTO.getCpf()))
                .orElse(null);
        if (!Objects.isNull(pessoa)) {
            throw new NegocioExeption("Pessoa ja cadastrada com este cpf");
        }
        pessoa = PessoaMapper.toModel(pessoaDTO);
        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        pessoa = pessoaRepository.save(pessoa);;
        return PessoaMapper.toDTOresumido(pessoa);
    }

    @Transactional
    public PessoaDTO atualizar(PessoaDTO pessoaDTO, Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            throw new PessoaNaoEncontradaException("Não encontrado");
        }
        pessoa.get().setNome(pessoaDTO.getNome());
        pessoa.get().setCpf(pessoaDTO.getCpf());
        pessoa.get().setContato(pessoaDTO.getContato());
        pessoa.get().setSenha(passwordEncoder.encode(pessoaDTO.getSenha()));
        //pessoa.get().setTarefas(pessoaDTO.getTarefas().stream().map(tarefaDTO -> TarefaMapper.toModel(tarefaDTO)).collect(Collectors.toList()));

        return PessoaMapper.toDTOresumido(pessoaRepository.save(pessoa.get()));
    }



    @Transactional
    public void excluir(Long id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new PessoaNaoEncontradaException("Id not found " + id);
        }
    }

    @Transactional
    public Contato criarContato(Contato contato, Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            throw new PessoaNaoEncontradaException("Não encontrada");
        }
        contato.setPessoa(pessoa.get());
        return contatoRepository.save(contato);
    }
}
