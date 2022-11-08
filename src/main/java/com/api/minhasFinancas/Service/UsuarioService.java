package com.api.minhasFinancas.Service;

import com.api.minhasFinancas.Modelo.Entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
