package org.gerardo.desafio.topico.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gerardo.desafio.topico.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurytyFilter extends OncePerRequestFilter  {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    @Autowired
    public SecurytyFilter(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del encabezado Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Si no hay token o no empieza con "Bearer ", no se autentica
            handleUnauthorized(request, response); // Manejar no autorizado
            return;
        }

        try {
            String token = authHeader.substring(7); // Eliminar "Bearer " del token
            String nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario != null) {
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                handleUnauthorized(request, response); // Manejar no autorizado si no se puede validar el token
                return;
            }
        } catch (Exception e) {
            handleUnauthorized(request, response); // Manejar no autorizado en caso de excepción
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleUnauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("No tiene autorización para acceder a este recurso.");
    }
}