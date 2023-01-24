package com.testetecnico.cadpessoas.repositories;

import com.testetecnico.cadpessoas.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {}
