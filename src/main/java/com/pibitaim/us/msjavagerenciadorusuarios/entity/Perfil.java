package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TBUS007_CAD_PER_ACE")
@Data
public class Perfil {

    @Column(name = "COD_CAD_PER")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perfilId;

    @Column(name = "PER_PAP")
    @Enumerated(EnumType.STRING)
    private EnumPapel papel;

    @Column(name = "PER_PRM")
    @Enumerated(EnumType.STRING)
    private EnumPermissao permissao;

    @Override
    public String toString() {
        return "Perfil{" +
                "perfilId=" + perfilId +
                ", papel=" + papel.getPapel() +
                ", permissao=" + permissao.getPermissao() +
                '}';
    }
}
