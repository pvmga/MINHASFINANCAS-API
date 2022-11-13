package com.api.minhasFinancas.Service;

import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Modelo.Repository.UsuarioRepository;

import com.api.minhasFinancas.Service.Impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
// Test Unitário
public class UsuarioServiceTest {

    //@Autowired
    UsuarioService service;
    //@Autowired
    @MockBean
    UsuarioRepository repository;

    @BeforeEach
    public void setUp() {
        //UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);
        //repository = Mockito.mock(UsuarioRepository.class); //Adicionando a anotation @MockBean não preciso utilizar esse trecho do código.
        service = new UsuarioServiceImpl(repository);
    }

    @Test
    public void deveValidarEmail() {
        
        // cenario
        //repository.deleteAll();
        Mockito.when(repository.existsByEmail(Mockito.anyString()))
                .thenReturn(false);

        // acao
        service.validarEmail("email@email.com");

    }

    @Test
    public void deveLancarErroQuandoExistirEmailCadastrado() {
        // cenário
        //Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
        //repository.save(usuario);
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        // acao
        service.validarEmail("email@email.com");
    }
}
