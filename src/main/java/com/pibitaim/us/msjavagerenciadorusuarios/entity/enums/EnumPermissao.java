package com.pibitaim.us.msjavagerenciadorusuarios.entity.enums;

public enum EnumPermissao {

    LEITURA("LEITURA"), ESCRITA("ESCRITA"), ATUALIZACAO("ATUALIZACAO"), DELECAO("DELECAO");

    private String permissao;

    EnumPermissao(String permissao){
        this.permissao = permissao.toUpperCase();
    }

    public String getPermissao() {
        return permissao;
    }

}
