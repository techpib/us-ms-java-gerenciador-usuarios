package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.UsuarioDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.UsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.UsuarioFormPerfil;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        UsuarioMapper.class,
        Usuario.class,
        UsuarioDTO.class,
        UsuarioForm.class,
        UsuarioFormPerfil.class
})
public class UsuarioMapperTest {

    private final String EMAIL_CADASTRO_UPPER = "EMAIL@TESTE.COM";
    private final String EMAIL_CADASTRO_LOWER = "email@teste.com";

    @Mock
    private Usuario mockUsuario;

    @Mock
    private Page<Usuario> mockUsuarios;

    @Mock
    private UsuarioForm mockUsuarioForm;

    @Mock
    private UsuarioFormPerfil mockUsuarioFormPerfil;

    @Mock
    private UsuarioMapper mockUsuarioMapper;

    @Autowired
    private UsuarioForm usuarioForm;

    @Autowired
    private UsuarioFormPerfil usuarioFormPerfil;

    @Autowired
    private Usuario usuario;

    @Autowired
    private UsuarioMapper usuarioMapper;

    private List listUsuarios;

    @BeforeEach
    private void inicializar(){
        usuarioForm.setEmailUsuario(EMAIL_CADASTRO_UPPER);
        usuarioFormPerfil.setEmailUsuario(EMAIL_CADASTRO_UPPER);
        listUsuarios = new ArrayList();
    }

    @Test
    @DisplayName("deveEfetuarConversaoParaDTO: Deve efetuar a conversao de Usuario para UsuaioDTO")
    public void deveEfetuarConversaoDeUsuarioParaUsuarioDTO(){
        when(mockUsuarioMapper.converteParaDTO(any(Usuario.class))).thenReturn(new UsuarioDTO());
        assertEquals(UsuarioDTO.class, mockUsuarioMapper.converteParaDTO(mockUsuario).getClass());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeUsuarioParaUsuarioDTONulo: Deve efetuar a conversao de Usuario para UsuaioDTO nulo")
    public void deveEfetuarConversaoDeUsuarioParaUsuarioDTONulo(){
        when(mockUsuarioMapper.converteParaDTO(any(Usuario.class))).thenReturn(null);
        assertNull(mockUsuarioMapper.converteParaDTO(mockUsuario));
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeUsuarioFormParaEntity: Deve efetuar a conversao de UsuarioForm para Usuario")
    public void deveEfetuarConversaoDeUsuarioFormParaEntity(){
        usuario = usuarioMapper.converteParaEntity(usuarioForm, "teste");

        assertTrue(usuario instanceof Usuario);
        assertEquals("teste", usuario.getSenhaAcessoUsuario());
        assertNotNull(usuario.getDataUltimaAtualizacao());
        assertEquals(EMAIL_CADASTRO_LOWER, usuario.getEmailUsuario());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeUsuarioFormParaEntityNulo: Deve efetuar a conversao de UsuarioForm para Usuario nulo")
    public void deveEfetuarConversaoDeUsuarioFormParaEntityNulo(){
        when(mockUsuarioMapper.converteParaEntity(any(UsuarioForm.class), any(String.class))).thenReturn(null);
        assertNull(mockUsuarioMapper.converteParaEntity(usuarioForm, "teste"));
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeUsuarioperfilParaEntity: Deve efetuar a conversao de UsuarioFormPerfil para Usuario")
    public void deveEfetuarConversaoDeUsuarioFormPerfilParaEntity(){
        usuario = usuarioMapper.converteParaEntity(usuarioFormPerfil, "teste");

        assertTrue(usuario instanceof Usuario);
        assertEquals("teste", usuario.getSenhaAcessoUsuario());
        assertNotNull(usuario.getDataUltimaAtualizacao());
        assertEquals(EMAIL_CADASTRO_LOWER, usuario.getEmailUsuario());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeUsuarioFormPerfilParaEntityNulo: Deve efetuar a conversao de UsuarioFormPerfil para Usuario nulo")
    public void deveEfetuarConversaoDeUsuarioFormPerfilParaEntityNulo(){
        when(mockUsuarioMapper.converteParaEntity(any(UsuarioFormPerfil.class), any(String.class))).thenReturn(null);
        assertNull(mockUsuarioMapper.converteParaEntity(new UsuarioFormPerfil(), "teste"));
    }

    @Test
    @DisplayName("deveEfetuarConversaoDePageUsuarioParaUsuarioDTO: Deve efetuar a conversao de Page<Usuario> para UsuaioDTO")
    public void deveEfetuarConversaoDePageUsuarioParaUsuarioDTO(){
        Page<UsuarioDTO> usuariosPage = new PageImpl<UsuarioDTO>(listUsuarios);
        when(mockUsuarioMapper.converteParaDTO(any(Page.class))).thenReturn(usuariosPage);
        assertTrue(mockUsuarioMapper.converteParaDTO(mockUsuarios) instanceof Page);
    }

    @Test
    @DisplayName("deveEfetuarConversaoDePageUsuarioParaUsuarioDTONulo: Deve efetuar a conversao de Page<Usuario> para UsuaioDTO nulo")
    public void deveEfetuarConversaoDePageUsuarioParaUsuarioDTONulo(){
        when(mockUsuarioMapper.converteParaDTO(any(Page.class))).thenReturn(null);
        assertNull(mockUsuarioMapper.converteParaDTO(mockUsuarios));
    }


}