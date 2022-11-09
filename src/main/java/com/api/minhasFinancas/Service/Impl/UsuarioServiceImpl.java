package com.api.minhasFinancas.Service.Impl;

import com.api.minhasFinancas.Exception.RegraNegocioException;
import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Modelo.Repository.UsuarioRepository;
import com.api.minhasFinancas.Service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    //@Autowired
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
        boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }
}
