package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoEndereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumUF;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EnderecoAtributosForm {

    @NotNull
    private boolean enderecoPrincipal;

    @Size(max = 8)
    @NotNull
    @NotBlank
    private String cepEndereco;

    @NotNull
    private EnumUF uf;

    @NotNull
    @NotBlank
    private String municipioEndereco;

    @NotNull
    @NotBlank
    private String bairroEndereco;

    @NotNull
    @NotBlank
    private String logradouroEndereco;

    private String complementoEndereco;

    @NotNull
    private EnumTipoEndereco tipoEndereco;

}
