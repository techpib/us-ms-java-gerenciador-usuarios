package com.pibitaim.us.msjavagerenciadorusuarios.service.impl;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.EnderecosUsuarioRepository;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.EnderecosUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecosUsuarioServiceImpl implements EnderecosUsuarioService {

    @Autowired
    EnderecosUsuarioRepository enderecosUsuarioRepository;

    @Override
    public Integer qtdEnderecosCodUsuario(String codUsuario) {
        return enderecosUsuarioRepository.qtdEnderecosPorCodUsuario(codUsuario);
    }

    @Override
    public Integer getUltimoNivelPrioridade(String codUsuario) {
        return enderecosUsuarioRepository.getUltimoNivelPrioridade(codUsuario);
    }

    @Override
    public boolean existeEnderecosCadastradosParaCodUsuario(String codUsuario) {
        return enderecosUsuarioRepository.existeEnderecosCadastradosParaCodigoUsuario(codUsuario).isPresent();
    }

    @Override
    public void atualizaEnderecosPrincipais(String codUsuario) {
        enderecosUsuarioRepository.atualizaEnderecosPrincipais(codUsuario);
    }

    @Override
    public EnderecosUsuario save(EnderecosUsuario enderecosUsuario) {
        return enderecosUsuarioRepository.save(enderecosUsuario);
    }

    @Override
    public Optional<List<EnderecosUsuario>> findByEnderecoCodCadastroEndereco(Long codCadastroEndereco) {
        return enderecosUsuarioRepository.findByEnderecoCodCadastroEndereco(codCadastroEndereco);
    }

    @Override
    public Optional<List<EnderecosUsuario>> findCodCadastroEnderecoByCodUsuario(String codUsuario) {
        return enderecosUsuarioRepository.findCodCadastroEnderecoByCodUsuario(codUsuario);
    }

}
