package com.testetecnico.cadpessoas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.testetecnico.cadpessoas.domain.Endereco;
import com.testetecnico.cadpessoas.domain.Pessoa;
import com.testetecnico.cadpessoas.domain.dtos.EnderecoDTO;
import com.testetecnico.cadpessoas.repositories.EnderecoRepository;
import com.testetecnico.cadpessoas.repositories.PessoaRepository;
import com.testetecnico.cadpessoas.services.exceptions.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EnderecoServiceTest {
  private static final int ID_ENDERECO = 1;
  private static final int ID_PESSOA = 1;
  private static final String LOGRADOURO = "LOGRADOURO TEST";
  private static final String CEP = "CEP TEST";
  private static final String NUMERO = "NUMERO TEST";
  private static final String CIDADE = "CIDADE TEST";
  private static final Object ENDERECO_PRINCIPAL_JA_EXISTE =
    "Endereço principal já existe";

  @InjectMocks
  private EnderecoService enderecoService;

  @InjectMocks
  private PessoaService pessoaService;

  @Mock
  private EnderecoRepository repository;

  @Mock
  private PessoaRepository pessoaRepository;

  private Endereco endereco;
  private EnderecoDTO enderecoDTO;
  private Pessoa pessoa;
  private List<Endereco> listaEndereco = new ArrayList<>();
  private Optional<Pessoa> optionalPessoa;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startEndereco();
  }

  @Test
  void whenCreateThenReturnSuccess() {
    when(pessoaRepository.findById(ID_PESSOA)).thenReturn(optionalPessoa);
    when(repository.save(any())).thenReturn(endereco);

    Endereco response = enderecoService.create(enderecoDTO);

    assertNotNull(response);
    assertEquals(Endereco.class, response.getClass());
    assertEquals(ID_ENDERECO, response.getId());
    assertEquals(LOGRADOURO, response.getLogradouro());
    assertEquals(CEP, response.getCep());
    assertEquals(NUMERO, response.getNumero());
    assertEquals(CIDADE, response.getCidade());
    assertEquals(ID_PESSOA, response.getPessoa().getId());
  }

  @Test
  void whenCreateThenReturnDataIntegrityViolationException() {
    when(pessoaRepository.findById(ID_PESSOA)).thenReturn(optionalPessoa);
    when(repository.save(endereco)).thenReturn(endereco);

    Pessoa response = pessoaService.findById(ID_PESSOA);

    assertNotNull(response.getEnderecos());
    assertEquals(Endereco.class, response.getEnderecos().get(0).getClass());

    //como se o endereço viesse verdadeiro agora para o endereço principal
    response.getEnderecos().get(0).setPrincipalEnd(true);
    try {
      enderecoDTO = new EnderecoDTO(response.getEnderecos().get(0));
      enderecoService.create(enderecoDTO);
    } catch (Exception ex) {
      assertEquals(DataIntegrityViolationException.class, ex.getClass());
      assertEquals(ENDERECO_PRINCIPAL_JA_EXISTE, ex.getMessage());
    }
  }

  void startEndereco() {
    // Criar um "Calendar", para inserir a data de nascimento de "Pessoa"
    Calendar dn = Calendar.getInstance();
    dn.set(1996, 6, 31);

    // Instanciação de Pessoa
    pessoa = new Pessoa(ID_PESSOA, "Rafael", dn.getTime());

    // Instanciação de Endereco
    endereco =
      new Endereco(ID_ENDERECO, LOGRADOURO, CEP, NUMERO, CIDADE, pessoa, true);
    listaEndereco.add(endereco);
    pessoa.setEnderecos(listaEndereco);

    enderecoDTO = new EnderecoDTO(endereco);
    enderecoDTO.setPrincipalEnd(false);
    optionalPessoa = Optional.of(new Pessoa(ID_PESSOA, "Rafael", dn.getTime(), listaEndereco));
  }
}
