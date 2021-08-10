package com.pibitaim.us.msjavagerenciadorusuarios.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EnderecosUsuarioDTO {

    private EnderecoDTO endereco;
    private Integer nivelPrioridade;
    private Boolean enderecoPrincipal;

}
