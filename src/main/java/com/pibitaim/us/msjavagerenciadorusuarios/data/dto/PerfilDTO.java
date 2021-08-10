package com.pibitaim.us.msjavagerenciadorusuarios.data.dto;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;
import lombok.Data;

@Data
public class PerfilDTO {

    private Long perfilId;
    private EnumPapel papel;
    private EnumPermissao permissao;

}
