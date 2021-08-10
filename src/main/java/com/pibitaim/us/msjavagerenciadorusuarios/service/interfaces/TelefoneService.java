package com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TelefoneService {

    Page<Telefone> findAll(Pageable paginacao);

    Optional<Telefone> findById(Long id);

    Page<Telefone> findByUsuarioId(Pageable paginacao, Long usuarioCpfCnpj);

    Telefone save(Telefone telefone);

    void deleteByCodigoCadastroTelefone(Long codigoCadastroTelefone);
}
