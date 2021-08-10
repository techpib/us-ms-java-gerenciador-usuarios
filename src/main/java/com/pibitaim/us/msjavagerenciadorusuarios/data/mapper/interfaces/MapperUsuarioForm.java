package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces;

public interface MapperUsuarioForm<Entity, Form> {

    Entity converteParaEntity(Form form, String senhaInicial);

}