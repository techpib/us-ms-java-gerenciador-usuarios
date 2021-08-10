package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces;

public interface MapperForm<Entity, Form> {

    Entity converteParaEntity(Form form);
}
