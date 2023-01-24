package com.testetecnico.cadpessoas.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.repositories.EnderecoRepository;
import com.testetecnico.cadpessoas.repositories.PessoaRepository;

@Service
public class DBService {
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public void instanciaDB(){
        // Criar um "Calendar", para inserir a data de nascimento de "Pessoa"
        Calendar dn = Calendar.getInstance();
        dn.set(1996, 6, 31); // o mês, tem que colocar um número a menos, pois ele incrementa +1.

        // Instanciação de Pessoa
        Pessoa p = new Pessoa(null, "Rafael", dn.getTime());

        // Preparando uma lista simples de endereços
        List<Endereco> enderecos = new ArrayList<>();
        Endereco e = new Endereco(null, "Rua Bruno Vieira", "12345-789", "1234", "Joaquinópolis", p, false);
        enderecos.add(e);
        e = new Endereco(null, "Rua Juca Vieira", "12456-789", "4321", "Mariquinópolis", p, true);
        
        // Adicionando a variável enderecos
        enderecos.add(e);

        // Inserindo lista de endereços
        p.setEnderecos(enderecos);
        
        // Salvando no BD
        pessoaRepository.save(p);
        enderecoRepository.saveAll(enderecos);
    }
}
