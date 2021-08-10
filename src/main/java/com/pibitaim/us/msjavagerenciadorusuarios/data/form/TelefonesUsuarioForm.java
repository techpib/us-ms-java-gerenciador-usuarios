package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class TelefonesUsuarioForm {

    @NotNull
    private Long codigoCadastroTelefone;

    @NotNull
    private Long cpfCnpjUsuario;

    @NotNull
    private Integer nivelPrioridade;

    @NotNull
    private Boolean telefonePrincipal;

}
