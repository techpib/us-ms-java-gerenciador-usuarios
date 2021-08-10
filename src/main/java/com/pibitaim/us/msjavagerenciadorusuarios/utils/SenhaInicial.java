package com.pibitaim.us.msjavagerenciadorusuarios.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SenhaInicial {

    private EncoderMD5 encoderMD5;

    public static String geraSenhaInicial() throws NoSuchAlgorithmException {
        try {
            int charInicial = 48; // 0 no ASCII
            int charFinal = 122; // z no ASCII
            int tamSenha = 10;

            String senhaRandom = new Random().ints(charInicial, charFinal+1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(tamSenha)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            return new EncoderMD5().encodeToMD5(senhaRandom);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Erro na geração da senha, contate um administrador do sistema");
        }
    }

}
