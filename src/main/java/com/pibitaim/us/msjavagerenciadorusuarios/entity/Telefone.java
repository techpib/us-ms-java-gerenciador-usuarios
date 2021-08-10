package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoTelefone;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "TBUS003_CAD_TEL_USU")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_CAD_TEL")
    private Long codigoCadastroTelefone;

    @NotNull
    @Column(name = "DAT_ULT_ATU")
    private Date dataUltimaAtualizacao;

    @Min(0)
    @Column(name = "TEL_DDD", length = 2)
    private Integer telefoneDDD;

    @Min(0)
    @Column(name = "TEL_NUM", length = 9)
    private Integer telefoneNumero;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIP_TEL", columnDefinition = "CHAR(01)")
    private EnumTipoTelefone tipoTelefone;

    @OneToMany(
            mappedBy = "telefone",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TelefonesUsuario> telefonesUsuario = new ArrayList<>();

    @Override
    public String toString() {
        return "Telefone{" +
                "codigoCadastroTelefone=" + codigoCadastroTelefone +
                ", dataUltimaAtualizacao=" + dataUltimaAtualizacao +
                ", telefoneDDD=" + telefoneDDD +
                ", telefoneNumero=" + telefoneNumero +
                ", tipoTelefone=" + tipoTelefone +
                ", telefonesUsuarios=" + telefonesUsuario.toString() +
                '}';
    }
}
