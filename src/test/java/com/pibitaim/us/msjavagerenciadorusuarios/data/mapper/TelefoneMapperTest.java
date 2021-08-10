package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.TelefoneDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.TelefoneForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuarioId;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        TelefoneMapper.class,
        Telefone.class,
        TelefoneDTO.class,
        TelefoneForm.class,
        TelefonesUsuario.class
})
public class TelefoneMapperTest {

    @Mock
    private Telefone mockTelefone;

    @Mock
    private Page<Telefone> mockTelefones;

    @Mock
    private TelefoneMapper mockTelefoneMapper;

    @Autowired
    private TelefoneForm telefoneForm;

    @Autowired
    private Telefone telefone;

    @Autowired
    private TelefoneMapper telefoneMapper;

    private List listTelefones;

    private List<TelefonesUsuario> listTelefonesUsuario;

    private final Long COD_CADASTRO_TELEFONE = 1L;

    @BeforeEach
    private void inicializar(){
        carregarListaTelefonesUsuarios();
        listTelefones = new ArrayList();
    }

    @Test
    @DisplayName("deveEfetuarConversaoParaDTO: Deve efetuar a conversao de Telefone para TelefoneDTO")
    public void deveEfetuarConversaoDeTelefoneParaTelefoneDTO(){
        when(mockTelefoneMapper.converteParaDTO(any(Telefone.class))).thenReturn(new TelefoneDTO());
        assertEquals(TelefoneDTO.class, mockTelefoneMapper.converteParaDTO(mockTelefone).getClass());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefoneParaTelefoneDTONulo: Deve efetuar a conversao de Telefone para TelefoneDTO nulo")
    public void deveEfetuarConversaoDeTelefoneParaTelefoneDTONulo(){
        when(mockTelefoneMapper.converteParaDTO(any(Telefone.class))).thenReturn(null);
        assertNull(mockTelefoneMapper.converteParaDTO(mockTelefone));
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefoneFormParaEntity: Deve efetuar a conversao de TelefoneForm para Telefone")
    public void deveEfetuarConversaoDeTelefoneFormParaEntity(){
        telefone = telefoneMapper.converteParaEntity(mock(TelefoneForm.class));

        assertTrue(telefone instanceof Telefone);
        assertNotNull(telefone.getDataUltimaAtualizacao());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefoneFormParaEntityNulo: Deve efetuar a conversao de TelefoneForm para Telefone nulo")
    public void deveEfetuarConversaoDeTelefoneFormParaEntityNulo(){
        when(mockTelefoneMapper.converteParaEntity(any(TelefoneForm.class))).thenReturn(null);
        assertNull(mockTelefoneMapper.converteParaEntity(telefoneForm));
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefoneFormParaEntityComListaECodCadastro: Deve efetuar a conversao de TelefoneForm para Telefone, com lista de telefones e codCadastro")
    public void deveEfetuarConversaoDeTelefoneFormParaEntityComListaECodCadastro(){
        telefone = telefoneMapper.converteParaEntity(telefoneForm, COD_CADASTRO_TELEFONE, listTelefonesUsuario);

        assertTrue(telefone instanceof Telefone);
        assertTrue(telefone.getDataUltimaAtualizacao() instanceof Date);
        assertEquals(COD_CADASTRO_TELEFONE, telefone.getCodigoCadastroTelefone());
        assertTrue(telefone.getTelefonesUsuario().size() == 3);
        assertNotNull(telefone.getDataUltimaAtualizacao());
        assertNotNull(telefone.getCodigoCadastroTelefone());
        assertNotNull(telefone.getTelefonesUsuario());
    }

    @Test
    @DisplayName("deveEfetuarConversaoDeTelefoneFormParaEntityComListaECodCadastroNulo: Deve efetuar a conversao de TelefoneForm para Telefone, com lista de telefones e codCadastro nulo")
    public void deveEfetuarConversaoDeTelefoneFormParaEntityComListaECodCadastroNulo(){
        when(mockTelefoneMapper.converteParaEntity(any(TelefoneForm.class), any(Long.class), any(List.class))).thenReturn(null);
        assertNull(mockTelefoneMapper.converteParaEntity(telefoneForm, COD_CADASTRO_TELEFONE, listTelefonesUsuario));
    }

    @Test
    @DisplayName("deveEfetuarConversaoDePageTelefoneParaTelefoneDTO: Deve efetuar a conversao de Page<Telefone> para TelefoneDTO")
    public void deveEfetuarConversaoDePageTelefoneParaTelefoneDTO(){
        Page<TelefoneDTO> telefonesPage = new PageImpl<TelefoneDTO>(listTelefones);
        when(mockTelefoneMapper.converteParaDTO(any(Page.class))).thenReturn(telefonesPage);
        assertTrue(mockTelefoneMapper.converteParaDTO(mockTelefones) instanceof Page);
    }

    @Test
    @DisplayName("deveEfetuarConversaoDePageTelefoneParaTelefoneDTONulo: Deve efetuar a conversao de Page<Telefone> para TelefoneDTO nulo")
    public void deveEfetuarConversaoDePageTelefoneParaTelefoneDTONulo(){
        when(mockTelefoneMapper.converteParaDTO(any(Page.class))).thenReturn(null);
        assertNull(mockTelefoneMapper.converteParaDTO(mockTelefones));
    }

    private void carregarListaTelefonesUsuarios(){
        listTelefonesUsuario = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            TelefonesUsuario telefonesUsuario = new TelefonesUsuario();
            telefonesUsuario.setTelefonesUsuarioId(mock(TelefonesUsuarioId.class));
            telefonesUsuario.setUsuario(mock(Usuario.class));
            telefonesUsuario.setTelefone(mock(Telefone.class));
            telefonesUsuario.setNivelPrioridade(i);
            telefonesUsuario.setTelefonePrincipal(i == 0 ? true : false);
            telefonesUsuario.setDataUltimaAtualizacao(new Date());
            listTelefonesUsuario.add(telefonesUsuario);
        }
    }

}
