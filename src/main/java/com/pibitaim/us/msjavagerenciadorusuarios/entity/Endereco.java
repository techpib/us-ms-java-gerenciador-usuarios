package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoEndereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumUF;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "TBUS004_CAD_END_USU")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_CAD_END")
    private Long codigoCadastroEndereco;

    @NotNull
    @Column(name = "DAT_ULT_ATU")
    private Date dataUltimaAtualizacao;

    @NotNull
    @Min(0)
    @Digits(integer = 8, fraction = 0)
    @Column(name = "CEP_END")
    private Integer cepEndereco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "CHAR(02)", name = "UF_END")
    private EnumUF uf;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)", name = "MUN_END")
    private String municipioEndereco;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)", name = "BAI_END")
    private String bairroEndereco;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)", name = "LOG_END")
    private String logradouroEndereco;

    @Column(columnDefinition = "VARCHAR(255)", name = "COM_END")
    private String complementoEndereco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "CHAR(01)", name = "TIP_END")
    private EnumTipoEndereco tipoEndereco;

    @OneToMany(
            mappedBy = "endereco",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EnderecosUsuario> enderecosUsuario;

    @Override
    public String toString() {
        return "Endereco{" +
                "codigoCadastroEndereco=" + codigoCadastroEndereco +
                ", dataUltimaAtualizacao=" + dataUltimaAtualizacao +
                ", cepEndereco=" + cepEndereco +
                ", uf=" + uf +
                ", municipioEndereco='" + municipioEndereco + '\'' +
                ", bairroEndereco='" + bairroEndereco + '\'' +
                ", logradouroEndereco='" + logradouroEndereco + '\'' +
                ", complementoEndereco='" + complementoEndereco + '\'' +
                ", tipoEndereco=" + tipoEndereco +
                ", enderecosUsuario=" + enderecosUsuario +
                '}';
    }
}
