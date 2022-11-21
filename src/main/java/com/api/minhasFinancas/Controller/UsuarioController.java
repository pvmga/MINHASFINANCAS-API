package com.api.minhasFinancas.Controller;

import com.api.minhasFinancas.Controller.dto.UsuarioDTO;
import com.api.minhasFinancas.Exception.ErroAutenticacaoException;
import com.api.minhasFinancas.Exception.RegraNegocioException;
import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Service.LancamentoService;
import com.api.minhasFinancas.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;

    @GetMapping("/teste")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar ( @RequestBody UsuarioDTO dto ) {
        try {
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar( @RequestBody UsuarioDTO dto ) {

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha()).build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);

            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo( @PathVariable("id") Long id) {

        Optional<Usuario> usuario = service.obterPorId(id);
        if(!usuario.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);

        return ResponseEntity.ok(saldo);

    }

}
