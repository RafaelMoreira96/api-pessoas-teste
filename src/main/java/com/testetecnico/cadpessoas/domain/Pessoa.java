package com.testetecnico.cadpessoas.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Pessoa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @NotNull
  private String nome;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date dataNascimento;

  @JsonIgnore
  @OneToMany(mappedBy = "pessoa")
  private List<Endereco> enderecos = new ArrayList<>();

  public Pessoa(Integer id, String nome, Date dataNascimento, List<Endereco> enderecos) {
    this.id = id;
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.enderecos = enderecos;
  }

  public Pessoa(Integer id, String nome, Date dataNascimento) {
    this.id = id;
    this.nome = nome;
    this.dataNascimento = dataNascimento;
  }
}
