package com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EnderecoService {

    Page<Endereco> findAll(Pageable paginacao);

    Optional<Endereco> findById(Long id);

    Page<Endereco> findByUsuarioId(Pageable paginacao, Long usuarioCpfCnpj);

    Endereco save(Endereco endereco);

    void deleteByCodigoCadastroEndereco(Long codigoCadastroEndereco);

}
