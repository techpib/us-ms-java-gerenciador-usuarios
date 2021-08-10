package com.pibitaim.us.msjavagerenciadorusuarios.data.dto;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumEstadoCivil;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumSexoUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoPessoa;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class UsuarioDTO {

    private Long cpfCnpj;
    private String nomeUsuario;
    private String emailUsuario;
    private EnumTipoPessoa enumTipoPessoa;
    private EnumSexoUsuario enumSexoUsuario;
    private LocalDate dataNascimento;
    private EnumEstadoCivil enumEstadoCivil;
    private Boolean indicadorMembresia;
    private LocalDate dataBatismo;
    private Date dataUltimaAtualizacao;
    private List<EnderecosUsuarioDTO> enderecosUsuario;
    private List<TelefonesUsuarioDTO> telefonesUsuario;
    private List<PerfilDTO> perfisUsuario;

}
