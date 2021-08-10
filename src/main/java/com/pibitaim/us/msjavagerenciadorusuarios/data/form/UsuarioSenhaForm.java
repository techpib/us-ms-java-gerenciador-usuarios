package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UsuarioSenhaForm {

    @NotNull
    @NotBlank
    private String senhaAtual;

    @NotNull
    @NotBlank
    @Size(min = 10)
    private String novaSenha;

}