package com.testetecnico.cadpessoas.services;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.EnderecoDTO;
import com.testetecnico.cadpessoas.repositories.EnderecoRepository;
import com.testetecnico.cadpessoas.repositories.PessoaRepository;
import com.testetecnico.cadpessoas.services.exceptions.IdentityObjectException;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
  @Autowired
  private EnderecoRepository repository;

  @Autowired
  private PessoaRepository pessoaRepository;

  // Adicionar uma pessoa
  public Endereco create(@Valid EnderecoDTO eDTO) {
    // Instanciando o objeto endereco e o pessoa
    Endereco e = new Endereco();
    Pessoa p = new Pessoa();
    // Obtendo o objeto Pessoa, do atributo 'pessoa' do EnderecoDTO.
    Optional<Pessoa> obj = pessoaRepository.findById(eDTO.getPessoa());

    if (obj.isPresent()) {
      p = obj.get();
    }

    // Verificação de existência de um endereço principal, para que não haja dois
    if (eDTO.getPrincipalEnd().equals(true)) {
      for (Endereco temp : p.getEnderecos()) {
        if (temp.getPrincipalEnd().equals(true)) {
          throw new IdentityObjectException("Endereço principal já existe");
        }
      }
    }

    // Criando o objeto Endereco

    e.setId(null);
    e.setLogradouro(eDTO.getLogradouro());
    e.setCep(eDTO.getCep());
    e.setNumero(eDTO.getNumero());
    e.setCidade(eDTO.getCidade());
    e.setPessoa(p);
    e.setPrincipalEnd(eDTO.getPrincipalEnd());

    // Salvando
    return repository.save(e);
  }
}
