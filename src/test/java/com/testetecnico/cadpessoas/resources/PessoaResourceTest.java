package com.testetecnico.cadpessoas.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.PessoaDTO;
import com.testetecnico.cadpessoas.services.PessoaService;
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

@SpringBootTest
public class PessoaResourceTest {
  private static final int INDEX = 0;
  private static final int ID = 1;
  private static final String NOME = "RAFAEL";

  @InjectMocks
  private PessoaResource resource;

  @Mock
  private PessoaService service;

  private Endereco endereco;
  private List<Endereco> listaEndereco = new ArrayList<>();
  private Pessoa pessoa;
  private PessoaDTO pessoaDTO;
  private Calendar dn = Calendar.getInstance();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startPessoa();
  }

  @Test
  void whenFindByIdThenReturnSuccess() {
    when(service.findById(anyInt())).thenReturn(pessoa);

    dn.set(1996, 6, 31);

    ResponseEntity<PessoaDTO> response = resource.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(PessoaDTO.class, response.getBody().getClass());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NOME, response.getBody().getNome());
    assertEquals(
      dn.getTime().toString(),
      response.getBody().getDataNascimento().toString()
    );
    assertEquals(listaEndereco, response.getBody().getEnderecos());
  }

  @Test
  void whenFindAllThenReturnAListOfPessoaDTO() {
    when(service.findAll()).thenReturn(List.of(pessoa));

    ResponseEntity<List<PessoaDTO>> response = resource.findAll();

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ArrayList.class, response.getBody().getClass());
    assertEquals(PessoaDTO.class, response.getBody().get(INDEX).getClass());

    assertEquals(ID, response.getBody().get(INDEX).getId());
    assertEquals(NOME, response.getBody().get(INDEX).getNome());
    assertEquals(
      dn.getTime().toString(),
      response.getBody().get(INDEX).getDataNascimento().toString()
    );
    assertEquals(listaEndereco, response.getBody().get(INDEX).getEnderecos());
  }

  @Test
  void whenCreateThenReturnCreated() {
    when(service.create(any())).thenReturn(pessoa);

    ResponseEntity<PessoaDTO> response = resource.create(pessoaDTO);

    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getHeaders().get("Location"));
  }

  @Test
  void whenUpdateThenReturnSuccess() {
    when(service.update(ID, pessoaDTO)).thenReturn(pessoa);

    ResponseEntity<PessoaDTO> response = resource.update(ID, pessoaDTO);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(PessoaDTO.class, response.getBody().getClass());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NOME, response.getBody().getNome());
    assertEquals(
      dn.getTime().toString(),
      response.getBody().getDataNascimento().toString()
    );
    assertEquals(listaEndereco, response.getBody().getEnderecos());
  }

  private void startPessoa() {
    // Criar um "Calendar", para inserir a data de nascimento de "Pessoa"
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
  }
}
