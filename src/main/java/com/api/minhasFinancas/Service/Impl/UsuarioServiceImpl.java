package com.api.minhasFinancas.Service.Impl;

import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Modelo.Repository.UsuarioRepository;
import com.api.minhasFinancas.Service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        //super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {

    }
}
