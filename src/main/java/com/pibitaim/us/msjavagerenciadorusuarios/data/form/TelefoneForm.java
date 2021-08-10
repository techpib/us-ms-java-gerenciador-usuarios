package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoTelefone;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class TelefoneForm {

    @NotNull
    private Long cpfCnpjUsuario;

    @NotNull
    private boolean telefonePrincipal;

    @NotNull
    private Integer telefoneDDD;

    @NotNull
    private Integer telefoneNumero;

    @NotNull
    private EnumTipoTelefone tipoTelefone;

}
