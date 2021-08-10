package com.pibitaim.us.msjavagerenciadorusuarios.controller.utils;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.PerfilService;

import java.util.List;

public class PerfilUtils {

    public static boolean perfilExiste(PerfilService perfilService, Long id){
        return perfilService.existsById(id);
    }

    public static boolean perfilExiste(PerfilService perfilService, EnumPapel papel, EnumPermissao permissao){
        return perfilService.existsByPapelPermissao(papel, permissao);
    }

    public static boolean perfisExistem(PerfilService perfilService, List<Long> perfis){
        return !perfis.stream()
                .map(p -> perfilService.existsById(p))
                .filter(p -> p == false).findFirst().isPresent();
    }

}
