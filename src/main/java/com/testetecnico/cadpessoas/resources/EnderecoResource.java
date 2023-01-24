package com.testetecnico.cadpessoas.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.dtos.EnderecoDTO;
import com.testetecnico.cadpessoas.services.EnderecoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {
  @Autowired
  private EnderecoService service;

  // Adicionar endereço à pessoa
  @PostMapping
  public ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoDTO eDTO){
   Endereco e = service.create(eDTO);
   URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(e.getId()).toUri();
   return ResponseEntity.created(uri).build();
  }

}
