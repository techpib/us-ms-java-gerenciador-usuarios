package com.pibitaim.us.msjavagerenciadorusuarios.service.impl;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.TelefoneRepository;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefoneServiceImpl implements TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Override
    public Page<Telefone> findAll(Pageable paginacao) {
        return telefoneRepository.findAll(paginacao);
    }

    @Override
    public Optional<Telefone> findById(Long id) {
        return telefoneRepository.findById(id);
    }

    @Override
    public Page<Telefone> findByUsuarioId(Pageable paginacao, Long usuarioCpfCnpj) {
        return telefoneRepository.findByUsuarioId(paginacao, usuarioCpfCnpj);
    }

    @Override
    public Telefone save(Telefone telefone) {
        return telefoneRepository.save(telefone);
    }

    @Override
    public void deleteByCodigoCadastroTelefone(Long codigoCadastroTelefone) {
        telefoneRepository.deleteByCodigoCadastroTelefone(codigoCadastroTelefone);
    }
}
