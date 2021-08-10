package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumTipoTelefone {

    F("TELEFONE_FIXO"), C("TELEFONE_CELULAR");

    private String tipoTelefone;

    EnumTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone.toUpperCase();
    }

    public String getTipoTelefone() {
        return tipoTelefone;
    }
}
