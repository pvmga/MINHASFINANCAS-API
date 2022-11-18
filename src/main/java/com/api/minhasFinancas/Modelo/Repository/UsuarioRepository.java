package com.api.minhasFinancas.Modelo.Repository;

import com.api.minhasFinancas.Modelo.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Retorna se existe ou não.
    boolean existsByEmail(String email);

    // Retorna cadastro caso exista / Query Methods
    Optional<Usuario> findByEmail(String email);

    // Pesquisando por Email e Nome.
    //Optional<Usuario> findByEmailAndNome(String email, String nome);
}
