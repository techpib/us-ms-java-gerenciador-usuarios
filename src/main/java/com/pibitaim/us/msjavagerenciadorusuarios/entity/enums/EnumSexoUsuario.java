package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumSexoUsuario {

    M("MASCULINO"), F("FEMININO");

    private String sexoUsuario;

    EnumSexoUsuario(String sexoUsuario){
        this.sexoUsuario = sexoUsuario.toUpperCase();
    }

    public String getSexoUsuario() {
        return sexoUsuario;
    }
}
