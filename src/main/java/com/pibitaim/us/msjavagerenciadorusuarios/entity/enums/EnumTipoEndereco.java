package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumTipoEndereco {

    C("CASA"), A("APARTAMENTO");

    private String tipoEndereco;

    EnumTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco.toUpperCase();
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }
}
