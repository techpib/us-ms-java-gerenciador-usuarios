package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumEstadoCivil {

    S("SOLTEIRO"),C("CASADO"), D("DIVORCIADO"), V("VIUVO");

    private String estadoCivil;

    EnumEstadoCivil(String estadoCivil){
        this.estadoCivil = estadoCivil.toUpperCase();
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }
}
