package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;

public interface MapperTelefonesUsuarioMapper <Entity, Form> {

    Entity converteParaEntity(Form form, Usuario usuario, Telefone telefone);

}
