package com.pibitaim.us.msjavagerenciadorusuarios.data.dto;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoEndereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumUF;
import lombok.Data;

import java.util.Date;

@Data
public class EnderecoDTO {

    private Long codigoCadastroEndereco;
    private Date dataUltimaAtualizacao;
    private Integer cepEndereco;
    private EnumUF uf;
    private String municipioEndereco;
    private String bairroEndereco;
    private String logradouroEndereco;
    private String complementoEndereco;
    private EnumTipoEndereco tipoEndereco;

}
