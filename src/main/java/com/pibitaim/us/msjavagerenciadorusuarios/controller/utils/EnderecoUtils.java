package com.pibitaim.us.msjavagerenciadorusuarios.controller.utils;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.EnderecoService;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.EnderecosUsuarioService;

import java.util.List;
import java.util.Optional;

public class EnderecoUtils {

    public static boolean enderecoExiste(EnderecoService enderecoService, Long id){
        return enderecoService.findById(id).isPresent();
    }

    public static Integer qtdEnderecosCodUsuario(EnderecosUsuarioService enderecosUsuarioService, String codUsuario){
        return enderecosUsuarioService.qtdEnderecosCodUsuario(codUsuario);
    }

    public static Integer getUltimoNivelPrioridade(EnderecosUsuarioService enderecosUsuarioService, String codUsuario){
        return enderecosUsuarioService.getUltimoNivelPrioridade(codUsuario);
    }

    public static boolean existeEnderecosCadastradosParaCodUsuario(EnderecosUsuarioService enderecosUsuarioService, String codUsuario){
        return enderecosUsuarioService.existeEnderecosCadastradosParaCodUsuario(codUsuario);
    }

    public static Optional<List<EnderecosUsuario>> findCodCadastroEnderecoByCodUsuario(EnderecosUsuarioService enderecosUsuarioService, String codUsuario){
        return enderecosUsuarioService.findCodCadastroEnderecoByCodUsuario(codUsuario);
    }

    public static boolean isEnderecoSemRelacionamento(EnderecosUsuarioService enderecosUsuarioService, Long codCadastroEndereco){
        return enderecosUsuarioService.findByEnderecoCodCadastroEndereco(codCadastroEndereco).isPresent() ? false : true;
    }

}
