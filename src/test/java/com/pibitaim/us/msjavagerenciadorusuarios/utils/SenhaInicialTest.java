package com.pibitaim.us.msjavagerenciadorusuarios.utils;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.security.NoSuchAlgorithmException;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@PrepareForTest(SenhaInicial.class)
@RunWith(PowerMockRunner.class)
public class SenhaInicialTest {

    @Test
    @DisplayName("geraSenhaInicial: Deve gerar a senha inicial")
    public void geraSenhaInicial() throws NoSuchAlgorithmException {
        if(SenhaInicial.geraSenhaInicial().getClass() != String.class){
            fail("Não gerou a senha como String");
        }
    }

    @Test
    @DisplayName("lancaExcecaoNoSuchAlgorithmException: Deve lançar a exceção NoSuchAlgorithmException")
    public void lancaExcecaoNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        PowerMockito.mockStatic(SenhaInicial.class);
        when(SenhaInicial.geraSenhaInicial()).thenThrow(new NoSuchAlgorithmException());
    }
}
