package com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;

import java.util.List;
import java.util.Optional;

public interface TelefonesUsuarioService {

    boolean existeTelefonesCadastradosParaCodUsuario(String codUsuario);

    void atualizaTelefonesPrincipais(String codUsuario);

    Integer qtdTelefonesCodUsuario(String codUsuario);

    Integer getUltimoNivelPrioridade(String codUsuario);

    TelefonesUsuario save(TelefonesUsuario telefonesUsuario);

    Optional<List<TelefonesUsuario>> findByTelefoneCodCadastroTelefone(Long id);

    Optional<List<TelefonesUsuario>> findCodCadastroTelefoneByCodUsuario(String codUsuario);

}
