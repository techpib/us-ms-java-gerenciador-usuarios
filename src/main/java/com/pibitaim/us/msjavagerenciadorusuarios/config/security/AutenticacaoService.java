package com.pibitaim.us.msjavagerenciadorusuarios.config.security;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpfCnpj) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByCpfCnpj(Long.parseLong(cpfCnpj));
        if(usuario.isPresent()){
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados inválidos");
    }

}
