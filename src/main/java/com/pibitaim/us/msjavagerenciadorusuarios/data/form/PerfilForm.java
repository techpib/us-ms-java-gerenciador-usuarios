package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PerfilForm {

    @NotNull
    private EnumPapel papel;

    @NotNull
    private EnumPermissao permissao;

}
