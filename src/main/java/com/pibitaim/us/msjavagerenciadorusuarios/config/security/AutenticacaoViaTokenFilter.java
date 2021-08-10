package com.pibitaim.us.msjavagerenciadorusuarios.config.security;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(httpServletRequest);
        boolean valido = tokenService.isTokenValido(token);

        if(valido){
            autenticarCliente(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String recuperarToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7, token.length());
    }

    private void autenticarCliente(String token){
        Usuario usuario = usuarioRepository.findByCodUsuario(tokenService.getIdUsuario(token).toString()).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
