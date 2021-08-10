package com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;

import java.util.List;
import java.util.Optional;

public interface EnderecosUsuarioService {

    Integer qtdEnderecosCodUsuario(String codUsuario);

    Integer getUltimoNivelPrioridade(String codUsuario);

    boolean existeEnderecosCadastradosParaCodUsuario(String codUsuario);

    void atualizaEnderecosPrincipais(String codUsuario);

    EnderecosUsuario save(EnderecosUsuario enderecosUsuario);

    Optional<List<EnderecosUsuario>> findByEnderecoCodCadastroEndereco(Long codCadastroEndereco);

    Optional<List<EnderecosUsuario>> findCodCadastroEnderecoByCodUsuario(String codUsuario);

}
