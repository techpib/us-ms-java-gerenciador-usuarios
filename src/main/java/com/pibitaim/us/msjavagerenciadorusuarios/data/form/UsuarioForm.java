package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumEstadoCivil;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumSexoUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoPessoa;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioForm {

    @NotNull
    private Long cpfCnpj;

    @NotNull
    @NotBlank
    @Length(min = 3)
    private String nomeUsuario;

    @NotNull
    @NotBlank
    private String emailUsuario;

    @NotNull
    private EnumTipoPessoa tipoPessoa;

    @NotNull
    private EnumSexoUsuario sexoUsuario;

    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    private EnumEstadoCivil estadoCivil;

    @NotNull
    private Boolean indicadorMembresia;

    private LocalDate dataBatismo;


}
