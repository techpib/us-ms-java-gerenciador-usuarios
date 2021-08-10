package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TBUS005_END_USU")
public class EnderecosUsuario {

    @EmbeddedId
    private EnderecosUsuarioId enderecosUsuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_IDE_USU")
    @MapsId("usuarioId")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_CAD_END")
    @MapsId("enderecoId")
    private Endereco endereco;

    @NotNull
    @Min(0)
    @Column(name = "NIV_PRIO")
    private Integer nivelPrioridade;

    @Column(name = "END_PRI")
    private Boolean enderecoPrincipal;

    @NotNull
    @Column(name = "DAT_ULT_ATU")
    private Date dataUltimaAtualizacao;

    @Override
    public String toString() {
        return "EnderecosUsuario{" +
                "enderecosUsuarioId=" + enderecosUsuarioId +
                ", nivelPrioridade=" + nivelPrioridade +
                ", enderecoPrincipal=" + enderecoPrincipal +
                ", dataUltimaAtualizacao=" + dataUltimaAtualizacao +
                '}';
    }
}
