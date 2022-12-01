package com.api.minhasFinancas.Api.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.api.minhasFinancas.Controller.UsuarioController;
import com.api.minhasFinancas.Controller.dto.UsuarioDTO;
import com.api.minhasFinancas.Exception.ErroAutenticacaoException;
import com.api.minhasFinancas.Exception.RegraNegocioException;
import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Service.LancamentoService;
import com.api.minhasFinancas.Service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test") // irá procurar nosso application de test
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    static final String API = "/api/usuarios";
    static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService service;

    @MockBean
    LancamentoService lancamentoService;

    @Test
    public void deveAutenticarUmUsuario() throws Exception {
        // cenário
        String email = "usuario@email.com";
        String senha = "123";

        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build(); // json que irá receber
        Usuario usuario = Usuario.builder().id(1l).email(email).senha(senha).build();

        Mockito.when(service.autenticar(email, senha)).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(dto);

        // execução e verificação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(API.concat("/autenticar"))
            .accept(JSON)
            .contentType(JSON)
            .content(json);

        mvc
            .perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
            .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }

    @Test
    public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
        // cenário
        String email = "usuario@email.com";
        String senha = "123";

        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build(); // json que irá receber
        Mockito.when(service.autenticar(email, senha)).thenThrow(ErroAutenticacaoException.class);
        String json = new ObjectMapper().writeValueAsString(dto);

        // execução e verificação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(API.concat("/autenticar"))
            .accept(JSON)
            .contentType(JSON)
            .content(json);

        mvc
            .perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveCriarUmNovoUsuario() throws Exception {
        // cenário
        String email = "usuario@email.com";
        String senha = "123";

        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build(); // json que irá receber
        Usuario usuario = Usuario.builder().id(1l).email(email).senha(senha).build();

        Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(dto);

        // execução e verificação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(API)
            .accept(JSON)
            .contentType(JSON)
            .content(json);

        mvc
            .perform(request)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
            .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
    }

    @Test
    public void deveRetornarBadRequestAoTentarCriarUmUsuarioInvalido() throws Exception {
        // cenário
        String email = "usuario@email.com";
        String senha = "123";

        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build(); // json que irá receber

        Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);

        String json = new ObjectMapper().writeValueAsString(dto);

        // execução e verificação
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(API)
            .accept(JSON)
            .contentType(JSON)
            .content(json);

        mvc
            .perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
