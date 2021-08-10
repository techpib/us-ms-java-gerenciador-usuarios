package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.form.TelefonesUsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        TelefonesUsuarioMapper.class,
        TelefonesUsuarioForm.class,
        TelefonesUsuario.class,
        Usuario.class,
        Telefone.class
})
public class TelefonesUsuarioMapperTest {

    @Mock
    private TelefonesUsuarioMapper mockTelefonesUsuarioMapper;

    @Autowired
    private TelefonesUsuarioForm telefonesUsuarioForm;

    @Autowired
    private TelefonesUsuario telefonesUsuario;

    @Autowired
    private Usuario usuario;

    @Autowired
    private Telefone telefone;

    @BeforeEach
    private void inicializar(){
        usuario.setCodUsuario(UUID.randomUUID());
        telefone.setCodigoCadastroTelefone(1L);
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefonesUsuarioFormParaTelefonesUsuario: Deve efetuar a conversao de TelefonesUsuarioForm para TelefonesUsuario")
    public void deveEfetuarConversaoDeTelefonesUsuarioFormParaTelefonesUsuario(){
        telefonesUsuario = new TelefonesUsuarioMapper().converteParaEntity(telefonesUsuarioForm, usuario, telefone);

        assertEquals(telefonesUsuario.getClass(), TelefonesUsuario.class);
        assertEquals(Date.class, telefonesUsuario.getDataUltimaAtualizacao().getClass());
        assertEquals(usuario, telefonesUsuario.getUsuario());
        assertEquals(telefone, telefonesUsuario.getTelefone());
        assertEquals(usuario.getCodUsuario(), telefonesUsuario.getTelefonesUsuarioId().getUsuarioId());
        assertEquals(1L, telefonesUsuario.getTelefonesUsuarioId().getTelefoneId());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefonesUsuarioFormParaTelefonesUsuarioNulo: Deve efetuar a conversao de TelefonesUsuarioForm para TelefonesUsuario null")
    public void deveEfetuarConversaoDeTelefonesUsuarioFormParaTelefonesUsuarioNulo(){
        when(mockTelefonesUsuarioMapper.converteParaEntity(any(TelefonesUsuarioForm.class), any(Usuario.class), any(Telefone.class))).thenReturn(null);
        assertNull(mockTelefonesUsuarioMapper.converteParaEntity(telefonesUsuarioForm, usuario, telefone));
    }

}
