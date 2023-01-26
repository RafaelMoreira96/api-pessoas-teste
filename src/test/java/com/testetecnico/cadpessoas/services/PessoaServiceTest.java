package com.testetecnico.cadpessoas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.PessoaDTO;
import com.testetecnico.cadpessoas.repositories.PessoaRepository;
import com.testetecnico.cadpessoas.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class PessoaServiceTest {
  private static final int ID = 1;
  private static final String NOME = "RAFAEL";
  private static final String OBJETO_NAO_ENCONTRADO = "Pessoa não encontrada! ID: " + ID;

  @InjectMocks
  private PessoaService pessoaService;

  @Mock
  private PessoaRepository repository;

  private Pessoa pessoa;
  private Endereco endereco;
  private List<Endereco> listaEndereco = new ArrayList<>();
  private PessoaDTO pessoaDTO;
  private Optional<Pessoa> optionalPessoa;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startPessoa();
  }

  // Teste para encontrar pessoa por ID
  @Test
  void whenFindByIdThenReturnAnPessoaInstance() {
    when(repository.findById(anyInt())).thenReturn(optionalPessoa);

    Calendar dn = Calendar.getInstance();
    dn.set(1996, 6, 31);

    Pessoa response = pessoaService.findById(ID);

    assertNotNull(response);
    assertEquals(Pessoa.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NOME, response.getNome());
    assertEquals(dn.getTime().toString(), response.getDataNascimento().toString());
    assertIterableEquals(listaEndereco, response.getEnderecos());
  }

  // Teste para Objeto Não Encontrado
  @Test
  void whenFindByIdThenReturnAnObjectNotFoundException() {
    when(repository.findById(anyInt()))
      .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

    try {
      pessoaService.findById(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
    }
  }

  @Test
  void whenFindAllThenReturnAnListOfPessoas() {
    when(repository.findAll()).thenReturn(List.of(pessoa));

    Calendar dn = Calendar.getInstance();
    dn.set(1996, 6, 31);

    List<Pessoa> response = pessoaService.findAll();
    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals(Pessoa.class, response.get(0).getClass());
    assertEquals(ID, response.get(0).getId());
    assertEquals(NOME, response.get(0).getNome());
    assertEquals(dn.getTime().toString(), response.get(0).getDataNascimento().toString());
    assertIterableEquals(listaEndereco, response.get(0).getEnderecos());
  }

  @Test
  void testCreate() {}

  @Test
  void testUpdate() {}

  private void startPessoa() {
    // Criar um "Calendar", para inserir a data de nascimento de "Pessoa"
    Calendar dn = Calendar.getInstance();
    dn.set(1996, 6, 31);

    // Instanciação de Pessoa
    pessoa = new Pessoa(ID, NOME, dn.getTime());

    // Instanciação de Endereco
    endereco =
      new Endereco(
        1,
        "Logra Test",
        "CEP Test",
        "Num Test",
        "City Test",
        pessoa,
        true
      );
    listaEndereco.add(endereco);
    pessoa.setEnderecos(listaEndereco);

    pessoaDTO = new PessoaDTO(pessoa);
    optionalPessoa =
      Optional.of(new Pessoa(ID, NOME, dn.getTime(), listaEndereco));
  }
}
