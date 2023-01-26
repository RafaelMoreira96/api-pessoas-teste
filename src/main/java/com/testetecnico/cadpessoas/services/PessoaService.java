package com.testetecnico.cadpessoas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.PessoaDTO;
import com.testetecnico.cadpessoas.repositories.PessoaRepository;
import com.testetecnico.cadpessoas.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class PessoaService {
  @Autowired
  private PessoaRepository repository;

  // Busca por ID
  public Pessoa findById(Integer id) {
    Optional<Pessoa> o = repository.findById(id);
    return o.orElseThrow(
      () -> new ObjectNotFoundException("Pessoa não encontrada! ID: " + id)
    );
  }

  // Listagem de todas as pessoas
  public List<Pessoa> findAll() {
    List<Pessoa> lista = repository.findAll();

    // Aqui é para remover todos os endereços que não seja o endereço principal
    for (Pessoa pessoa : lista) {
      List<Endereco> enderecosPrincipais = new ArrayList<>();
      for (Endereco endereco : pessoa.getEnderecos()) {
        if (endereco.getPrincipalEnd().equals(true)) {
          enderecosPrincipais.add(endereco);
        }
      }
      pessoa.setEnderecos(enderecosPrincipais);
    }

    return lista;
  }

  // Adicionar uma pessoa
  public Pessoa create(@Valid PessoaDTO pessoaDTO) {
    Pessoa p = new Pessoa();
    p.setId(null);
    p.setNome(pessoaDTO.getNome());
    p.setDataNascimento(pessoaDTO.getDataNascimento());
    p.setEnderecos(null);
    return repository.save(p);
  }

  // Atualizar informações de uma pessoa
  public Pessoa update(Integer id, @Valid PessoaDTO pessoaDTO) {
    Pessoa p = findById(id);
    
    p.setNome(pessoaDTO.getNome());
    p.setDataNascimento(pessoaDTO.getDataNascimento());

    return repository.save(p);
  }
}
