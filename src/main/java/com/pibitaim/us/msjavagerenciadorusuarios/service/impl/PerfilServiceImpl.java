package com.pibitaim.us.msjavagerenciadorusuarios.service.impl;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Perfil;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.PerfilRepository;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public Page<Perfil> findAll(Pageable paginacao) {
        return perfilRepository.findAll(paginacao);
    }

    @Override
    public Optional<Perfil> findById(Long id) {
        return perfilRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return perfilRepository.existsById(id);
    }

    @Override
    public boolean existsByPapelPermissao(EnumPapel papel, EnumPermissao permissao) {
        return perfilRepository.existsByPapelPermissao(papel.getPapel(), permissao.getPermissao()).isPresent();
    }

    @Override
    public Page<Perfil> findByUsuarioId(Pageable paginacao, Long usuarioCpfCnpj) {
        return perfilRepository.findByUsuarioId(paginacao, usuarioCpfCnpj);
    }

    @Override
    public Perfil save(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Override
    public void deleteById(Long id) {
        perfilRepository.deleteById(id);
    }
}
