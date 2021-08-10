package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumPapel {

    ADMIN("ADMIN"), MEMBRO("MEMBRO"), VISITANTE("VISITANTE");

    private String papel;

    EnumPapel(String papel){
        this.papel = papel.toUpperCase();
    }

    public String getPapel() {
        return papel;
    }

}
