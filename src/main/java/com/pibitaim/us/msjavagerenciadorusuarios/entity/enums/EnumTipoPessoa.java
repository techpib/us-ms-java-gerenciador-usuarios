package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumTipoPessoa {

    F("PF"), J("PJ");

    private final String tipoPessoa;

    EnumTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa.toUpperCase();
    }

    public String getTipoPessoa(){
        return this.tipoPessoa;
    }

}
