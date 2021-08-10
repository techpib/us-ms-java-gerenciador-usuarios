package com.pibitaim.us.msjavagerenciadorusuarios.utils;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.*;


@PrepareForTest(EncoderMD5.class)
@RunWith(PowerMockRunner.class)
public class EncoderMD5Test {

    @Test
    @DisplayName("efetuaEncodeToMD5: Deve efetuar o encode to texto para MD5")
    public void efetuaEncodeToMD5() throws NoSuchAlgorithmException {
        String texto = "abc";
        assertEquals("900150983cd24fb0d6963f7d28e17f72", EncoderMD5.encodeToMD5(texto));
    }

    @Test
    @DisplayName("lancaExcecaoNoSuchAlgorithmException: Deve lançar a exceção NoSuchAlgorithmException")
    public void lancaExcecaoNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        String texto = "abc";
        PowerMockito.mockStatic(EncoderMD5.class);
        when(EncoderMD5.encodeToMD5(texto)).thenThrow(new NoSuchAlgorithmException());
    }

    @Test
    @DisplayName("lancaExcecaoNullPointerException: Deve lançar a exceção NullPointerException")
    public void lancaExcecaoNullPointerException() {
        String texto = null;
        try {
            EncoderMD5.encodeToMD5(texto);
            fail("Não houve a exceção NullPointerException");
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            assertEquals(NullPointerException.class, exception.getClass());
        } catch (NoSuchAlgorithmException exception){
            fail("Houve a exceção NoSuchAlgorithmException, deveria ser NullPointerException");
        }
    }

}
