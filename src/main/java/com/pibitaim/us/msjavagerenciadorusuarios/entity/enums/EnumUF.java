package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumUF {

    AC("ACRE"),
    AL("ALAGOAS"),
    AP("AMAPA"),
    AM("AMAZONAS"),
    BA("BAHIA"),
    CE("CEARA"),
    DF("DISTRITO_FEDERAL"),
    ES("ESPIRITO_SANTO"),
    GO("GOIAS"),
    MA("MARANHAO"),
    MT("MATO_GROSSO"),
    MS("MATO_GROSSO_DO_SUL"),
    MG("MINAS_GERAIS"),
    PA("PARA"),
    PB("PARAIBA"),
    PR("PARANA"),
    PE("PERNAMBUCO"),
    PI("PIAUI"),
    RJ("RIO_DE_JANEIRO"),
    RN("RIO_GRANDE_DO_NORTE"),
    RS("RIO_GRANDE_DO_SUL"),
    RO("RONDONIA"),
    RR("RORAIMA"),
    SC("SANTA_CATARINA"),
    SP("SAO_PAULO"),
    SE("SERGIPE"),
    TO("TOCANTINS");

    private final String uf;

    EnumUF(String uf){
        this.uf = uf.toUpperCase();
    }

    public String getUf() {
        return this.uf;
    }
}
