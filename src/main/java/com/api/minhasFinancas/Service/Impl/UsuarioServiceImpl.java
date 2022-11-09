package com.api.minhasFinancas.Service.Impl;

import com.api.minhasFinancas.Exception.ErroAutenticacaoException;
import com.api.minhasFinancas.Exception.RegraNegocioException;
import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Modelo.Repository.UsuarioRepository;
import com.api.minhasFinancas.Service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ErroAutenticacaoException("Usuário não encontrado");
        }
        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoException("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }
}
