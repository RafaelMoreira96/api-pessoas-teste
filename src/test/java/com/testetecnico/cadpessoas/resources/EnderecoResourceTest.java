package com.testetecnico.cadpessoas.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.EnderecoDTO;
import com.testetecnico.cadpessoas.domain.dtos.PessoaDTO;
import com.testetecnico.cadpessoas.services.EnderecoService;

@SpringBootTest
public class EnderecoResourceTest {
  private static final int INDEX = 0;
  private static final int ID_ENDERECO = 1;
  private static final int ID_PESSOA = 1;
  private static final String LOGRADOURO = "RAFAEL";
  private static final String CEP = "CEP TEST";
  private static final String NUMERO = "NUMERO TEST";
  private static final String CIDADE = "CIDADE TEST";

  @InjectMocks
  private EnderecoResource resource;

  @Mock
  private EnderecoService service;

  private Endereco endereco;
  private List<Endereco> listaEndereco = new ArrayList<>();
  private EnderecoDTO enderecoDTO;
  private Pessoa pessoa;
  private Calendar dn = Calendar.getInstance();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEndereco();
  }

  @Test
  void whenCreateThenReturnCreated() {
    when(service.create(any())).thenReturn(endereco);

    ResponseEntity<EnderecoDTO> response = resource.create(enderecoDTO);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getHeaders().get("Location"));
  }

  private void startEndereco() {
    // Criar um "Calendar", para inserir a data de nascimento de "Pessoa"
    dn.set(1996, 6, 31);

    // Instanciação de Pessoa
    pessoa = new Pessoa(ID_PESSOA, "Rafael", dn.getTime());

    // Instanciação de Endereco
    endereco =
      new Endereco(ID_ENDERECO, LOGRADOURO, CEP, NUMERO, CIDADE, pessoa, true);
    listaEndereco.add(endereco);
    pessoa.setEnderecos(listaEndereco);
    enderecoDTO = new EnderecoDTO(endereco);
  }
}
