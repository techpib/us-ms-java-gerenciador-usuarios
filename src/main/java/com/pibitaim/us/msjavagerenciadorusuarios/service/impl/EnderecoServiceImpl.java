package com.pibitaim.us.msjavagerenciadorusuarios.service.impl;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Endereco;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.EnderecoRepository;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Page<Endereco> findAll(Pageable paginacao) {
        return enderecoRepository.findAll(paginacao);
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public Page<Endereco> findByUsuarioId(Pageable paginacao, Long usuarioCpfCnpj) {
        return enderecoRepository.findByUsuarioId(paginacao, usuarioCpfCnpj);
    }

    @Override
    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public void deleteByCodigoCadastroEndereco(Long codigoCadastroEndereco) {
        enderecoRepository.deleteByCodigoCadastroEndereco(codigoCadastroEndereco);
    }

}
