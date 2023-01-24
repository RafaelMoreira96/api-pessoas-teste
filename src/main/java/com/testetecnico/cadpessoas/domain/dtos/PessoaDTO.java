package com.testetecnico.cadpessoas.domain.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PessoaDTO {
  private Integer id;

  @NotNull
  private String nome;

  @NotNull
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataNascimento;
  
  private List<Endereco> enderecos = new ArrayList<>();

  public PessoaDTO(){}

  public PessoaDTO(Pessoa pessoa) {
    this.id = pessoa.getId();
    this.nome = pessoa.getNome();
    this.dataNascimento = pessoa.getDataNascimento();
    this.enderecos = pessoa.getEnderecos();
  }


}
