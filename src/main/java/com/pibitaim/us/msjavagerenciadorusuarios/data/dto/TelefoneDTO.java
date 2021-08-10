package com.pibitaim.us.msjavagerenciadorusuarios.data.dto;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoTelefone;
import lombok.Data;

import java.util.Date;

@Data
public class TelefoneDTO {

    private Long codigoCadastroTelefone;
    private Date dataUltimaAtualizacao;
    private Integer telefoneDDD;
    private Integer telefoneNumero;
    private EnumTipoTelefone tipoTelefone;

}
