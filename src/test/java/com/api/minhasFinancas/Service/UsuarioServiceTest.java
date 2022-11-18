package com.api.minhasFinancas.Service;

import com.api.minhasFinancas.Exception.ErroAutenticacaoException;
import com.api.minhasFinancas.Exception.RegraNegocioException;
import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Modelo.Repository.UsuarioRepository;

import com.api.minhasFinancas.Service.Impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
// Test Unitário
public class UsuarioServiceTest {

    //@Autowired // Caso ative setUp() necessitará remover o Autowired
    //UsuarioService service; // usamos até entrar o @SpyBean
    @SpyBean
    UsuarioServiceImpl service;

    //@Autowired // Caso ative setUp() necessitará remover o Autowired
    @MockBean
    UsuarioRepository repository;

    /*@BeforeEach
    public void setUp() {
        //UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);
        
        --- SÓ COM ESSE BLOCO CONSIGO FAZER O MOCK TAMBÉM ---
        //repository = Mockito.mock(UsuarioRepository.class); //Adicionando a anotation @MockBean não preciso utilizar esse trecho do código.
        //service = new UsuarioServiceImpl(repository); //usamos até implementar o @SpyBean
        --- /SÓ COM ESSE BLOCO CONSIGO FAZER O MOCK TAMBÉM ---

        //service = Mockito.spy(UsuarioServiceImpl.class); // Implementação padrão do @SpyBean, mas existe anotation para isso.
    }*/

    @Test
    public void deveSalvarUmUsuario() {
        // cenário
        // doNothing -> não deve validar quando invocar o método validarEmail
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());

        Usuario usuario = Usuario.builder()
                .id(1l)
                .nome("nome")
                .email("email@email.com")
                .senha("senha").build();

        // Quando chamar o método save passando qualquer usuário que seja, ele irá retornar o usuário do cenário
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //acao
        //Usuario usuarioSalvo = service.salvarUsuario(usuario);
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        // verificacao
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
        //cenario
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        // doThrow -> Jogue uma exceção ao chamar o validarEmail
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        //acao
        org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(usuario) ) ;

        //verificacao
        // Primeiro parametro é o Mock (repository), segundo, quantidade de vezes que ele chamou, nunca.
        Mockito.verify( repository, Mockito.never() ).save(usuario);

    }

    @Test()
    public void deveAutenticarUmUsuarioComSucesso() {
        // cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when( repository.findByEmail(email) ).thenReturn(Optional.of(usuario));

        //acao
        Usuario result = service.autenticar(email, senha);

        // verificacao
        Assertions.assertThat(result).isNotNull();
    }

    @Test()
    public void deveLancarErroQuandoNaoEncontradoUsuarioCadastradoComOEmailInformado() {
        // cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // acao
        //service.autenticar("email@mail.com", "senha");
        // Expressão lambida () ->
        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@mail.com", "senha") );

        // Usuário não encontrado para o email informado.
        Assertions.assertThat(exception)
                .isInstanceOf(ErroAutenticacaoException.class)
                .hasMessage("Usuário não encontrado para o email informado.");
    }

    @Test
    public void deveLancarErroQuandoSenhaNaoBater() {
        // cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();

        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        // acao
        //service.autenticar("email@mail.com", "123");
        // Expressão lambida () ->
        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123") );

        Assertions.assertThat(exception)
                .isInstanceOf(ErroAutenticacaoException.class)
                .hasMessage("Senha inválida.");
    }

    @Test
    public void deveValidarEmail() {
        
        // cenario
        //repository.deleteAll();
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        // acao
        service.validarEmail("email@email.com");

    }

    @Test
    public void deveLancarErroQuandoExistirEmailCadastrado() {
        // cenário
        //Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
        //repository.save(usuario);
        //Já existe um usuário cadastrado com este email.
        Mockito.when(repository.existsByEmail(Mockito.anyString()))
                .thenReturn(true);

        // acao
        Throwable exception = Assertions.catchThrowable( () -> service.validarEmail("email@email.com") );

        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("Já existe um usuário cadastrado com este email.");
    }
}
