package com.testetecnico.cadpessoas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.PessoaDTO;
import com.testetecnico.cadpessoas.repositories.PessoaRepository;
import com.testetecnico.cadpessoas.services.exceptions.ObjectNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PessoaServiceTest {
  private static final int ID = 1;
  private static final String NOME = "RAFAEL";
  private static final String EXCECAO = "Pessoa não encontrada! ID: " + ID;

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

    Pessoa response = pessoaService.findById(ID);

    assertNotNull(response);
    assertEquals(Pessoa.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NOME, response.getNome());
    assertIterableEquals(listaEndereco, response.getEnderecos());
  }

  // Teste para Objeto Não Encontrado
  @Test
  void whenFindByIdThenReturnAnObjectNotFoundException() {
    when(repository.findById(anyInt()))
      .thenThrow(
        new ObjectNotFoundException(EXCECAO)
      );

    try {
      pessoaService.findById(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(EXCECAO, ex.getMessage());
    }
  }

  @Test
  void testFindAll() {}

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
