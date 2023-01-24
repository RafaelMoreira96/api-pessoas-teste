package com.testetecnico.cadpessoas.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.PessoaDTO;
import com.testetecnico.cadpessoas.services.PessoaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {
  @Autowired
  private PessoaService service;

  // Busca por ID
  @GetMapping(value = "/{id}")
  public ResponseEntity<PessoaDTO> findById(@PathVariable Integer id) {
    Pessoa obj = service.findById(id);
    return ResponseEntity.ok().body(new PessoaDTO(obj));
  }

  // Listagem de todas as pessoas
  @GetMapping
  public ResponseEntity<List<PessoaDTO>> findAll() {
    List<Pessoa> pessoas = service.findAll();
    List<PessoaDTO> pessoasDTO = pessoas
      .stream()
      .map(obj -> new PessoaDTO(obj))
      .collect(Collectors.toList());
    return ResponseEntity.ok().body(pessoasDTO);
  }

  // Adicionar uma pessoa
  @PostMapping
  public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaDTO pDTO) {
    Pessoa p = service.create(pDTO);
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(p.getId())
      .toUri();
    return ResponseEntity.created(uri).build();
  }

  // Atualizar informações de uma pessoa
  @PutMapping(value = "/{id}")
  public ResponseEntity<PessoaDTO> update(
    @PathVariable Integer id,
    @RequestBody PessoaDTO pDTO
  ) {
    Pessoa p = service.update(id, pDTO);
    return ResponseEntity.ok().body(new PessoaDTO(p));
  }
}
