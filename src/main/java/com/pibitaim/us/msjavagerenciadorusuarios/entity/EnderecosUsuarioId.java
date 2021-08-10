package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EnderecosUsuarioId implements Serializable {

    @Type(type="uuid-char")
    @Column(name = "COD_IDE_USU")
    private UUID usuarioId;

    @Column(name = "COD_CAD_END")
    private Long enderecoId;

}
