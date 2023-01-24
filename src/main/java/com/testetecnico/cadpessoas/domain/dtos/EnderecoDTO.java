package com.testetecnico.cadpessoas.domain.dtos;

import com.testetecnico.cadpessoas.domain.Endereco;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnderecoDTO {
  private Integer id;

  @NotNull
  private String logradouro;

  @NotNull
  private String cep;

  @NotNull
  private String numero;

  @NotNull
  private String cidade;

  private Boolean principalEnd = false;
  private Integer pessoa;

  public EnderecoDTO() {}

  public EnderecoDTO(Endereco endereco) {
    this.id = endereco.getId();
    this.logradouro = endereco.getLogradouro();
    this.cep = endereco.getCep();
    this.numero = endereco.getNumero();
    this.cidade = endereco.getCidade();
    this.pessoa = endereco.getPessoa().getId();
    this.principalEnd = endereco.getPrincipalEnd();
  }
}
