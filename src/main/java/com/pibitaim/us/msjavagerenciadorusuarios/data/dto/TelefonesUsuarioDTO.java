package com.pibitaim.us.msjavagerenciadorusuarios.data.dto;

import lombok.Data;

@Data
public class TelefonesUsuarioDTO {

    private TelefoneDTO telefone;
    private Integer nivelPrioridade;
    private Boolean telefonePrincipal;

}
