package com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Perfil;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PerfilService {

    Page<Perfil> findAll(Pageable paginacao);

    Optional<Perfil> findById(Long id);

    boolean existsById(Long id);

    boolean existsByPapelPermissao(EnumPapel papel, EnumPermissao permissao);

    Page<Perfil> findByUsuarioId(Pageable paginacao, Long usuarioCpfCnpj);

    Perfil save(Perfil perfil);

    void deleteById(Long id);

}
