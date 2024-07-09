package org.gerardo.desafio.topico.controller;

import jakarta.validation.Valid;
import org.gerardo.desafio.topico.domain.usuarios.DatosAutenticacionUsuario;
import org.gerardo.desafio.topico.infra.security.DatosJWTToken;
import org.gerardo.desafio.topico.domain.usuarios.Usuario;
import org.gerardo.desafio.topico.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class autenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public autenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.clave());
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas: " + e.getMessage());
        }
    }
}
