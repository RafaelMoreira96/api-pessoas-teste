package com.testetecnico.cadpessoas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Endereco {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String logradouro;
  private String cep;
  private String numero;
  private String cidade;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "pessoa_id")
  private Pessoa pessoa;
  private Boolean principalEnd = false;
  public Endereco orElseThrow(Object object) {
    return null;
  }
}
