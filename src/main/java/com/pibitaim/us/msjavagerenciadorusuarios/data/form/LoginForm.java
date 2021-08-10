package com.pibitaim.us.msjavagerenciadorusuarios.data.form;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class LoginForm {

    private Long cpfCnpj;
    private String senha;

    public UsernamePasswordAuthenticationToken converter(){
        return new UsernamePasswordAuthenticationToken(this.cpfCnpj, this.senha);
    }

}
