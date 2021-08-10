package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Endereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;

public interface MapperEnderecosUsuarioMapper<Entity, Form> {

    Entity converteParaEntity(Form form, Usuario usuario, Endereco endereco);
}
