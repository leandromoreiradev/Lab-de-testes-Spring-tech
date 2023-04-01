package com.lemoreiradev.listadetarefa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemoreiradev.listadetarefa.domain.dto.PessoaDTO;
import com.lemoreiradev.listadetarefa.domain.exceptions.NegocioExeption;
import com.lemoreiradev.listadetarefa.domain.exceptions.PessoaNaoEncontradaException;
import com.lemoreiradev.listadetarefa.domain.mapper.PessoaMapper;
import com.lemoreiradev.listadetarefa.domain.model.Contato;
import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import com.lemoreiradev.listadetarefa.domain.repository.ContatoRepository;
import com.lemoreiradev.listadetarefa.domain.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ContatoRepository contatoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public static final String INDEX_PESSOAS = "pessoas";

    public ObjectMapper objectMapper;

    /*
     Para conferir dados no redis:
     Retornar lista = GET pessoas::listar
     Retornar um item ou recurso = GET pessoas::1 (o valor 1 é o id do item buscado, isso depende de quem foi definido como key)
    * */


    //@Cacheable(value = INDEX_PESSOAS) // aqui usamos sem o key
    @Cacheable(value = INDEX_PESSOAS,  key="#root.method.name") // Cacheia a lista de resultado e retonra pelo key no cli do redis
    public List<PessoaDTO> listar() {
        log.info("Listando pessoas");
        return pessoaRepository.findAll()
                .stream().map(PessoaMapper::toDTOresumido).collect(Collectors.toList());
    }



    @Cacheable(value = INDEX_PESSOAS, key = "#id") //Cacheia o resultado da busca e retona uma pessoa pelo key no cli do redis
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

    @CacheEvict(value = INDEX_PESSOAS, allEntries = true) // apaga todo cache quando cria um novo recurso
    @Transactional
    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = Optional.ofNullable(pessoaRepository.findByCpf(pessoaDTO.getCpf()))
                .orElse(null);
        if (!Objects.isNull(pessoa)) {
            log.info("ERROR: Pessoa cadastrada no sistema" );
            throw new NegocioExeption("Pessoa ja cadastrada com este cpf");
        }
        pessoa = PessoaMapper.toModel(pessoaDTO);
        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        pessoa = pessoaRepository.save(pessoa);;
        return PessoaMapper.toDTOresumido(pessoa);
    }

    @CacheEvict(value = INDEX_PESSOAS, allEntries = true)// apaga todo cache quando atualiza um recurso
    //@CachePut(value = INDEX_PESSOAS, key = "#id") // atualiza o cache de um unico recurso
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


    @CacheEvict(value = INDEX_PESSOAS, allEntries = true) // apaga todo cache quando um item é deletado
    @Transactional
    public void excluir(Long id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.info("ERROR: {}", e.getMessage());
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
