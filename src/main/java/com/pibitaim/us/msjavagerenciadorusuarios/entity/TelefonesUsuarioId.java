package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class TelefonesUsuarioId implements Serializable {

    @Type(type="uuid-char")
    @Column(name = "COD_IDE_USU")
    private UUID usuarioId;

    @Column(name = "COD_CAD_TEL")
    private Long telefoneId;
}
