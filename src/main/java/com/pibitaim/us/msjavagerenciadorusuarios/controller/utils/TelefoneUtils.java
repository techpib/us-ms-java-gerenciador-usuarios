package com.pibitaim.us.msjavagerenciadorusuarios.controller.utils;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.TelefoneService;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.TelefonesUsuarioService;

import java.util.List;
import java.util.Optional;

public class TelefoneUtils {

    public static boolean telefoneExiste(TelefoneService telefoneService, Long telefoneId){
        return telefoneService.findById(telefoneId).isPresent();
    }

    public static boolean existeTelefonesCadastradosParaCodUsuario(TelefonesUsuarioService telefonesUsuarioService, String codUsuario){
        return telefonesUsuarioService.existeTelefonesCadastradosParaCodUsuario(codUsuario);
    }

    public static Integer qtdTelefonesCodUsuario(TelefonesUsuarioService telefonesUsuarioService, String codUsuario){
        return telefonesUsuarioService.qtdTelefonesCodUsuario(codUsuario);
    }

    public static Integer getUltimoNivelPrioridade(TelefonesUsuarioService telefonesUsuarioService, String codUsuario){
        return telefonesUsuarioService.getUltimoNivelPrioridade(codUsuario);
    }

    public static Optional<List<TelefonesUsuario>> findCodCadastroTelefoneByCodUsuario(TelefonesUsuarioService telefonesUsuarioService, String codUsuario){
        return telefonesUsuarioService.findCodCadastroTelefoneByCodUsuario(codUsuario);
    }

    public static boolean isTelefoneSemRelacionamento(TelefonesUsuarioService telefonesUsuarioService, Long telefoneId){
        return telefonesUsuarioService.findByTelefoneCodCadastroTelefone(telefoneId).isPresent() ? false : true;
    }

}
