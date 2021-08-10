package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.form.TelefonesUsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperTelefonesUsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TelefonesUsuarioMapper implements MapperTelefonesUsuarioMapper<TelefonesUsuario, TelefonesUsuarioForm> {

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public TelefonesUsuario converteParaEntity(TelefonesUsuarioForm telefonesUsuarioForm, Usuario usuario, Telefone telefone) {
        return mapper.typeMap(TelefonesUsuarioForm.class, TelefonesUsuario.class).addMappings(mapper -> {
            mapper.map(src -> new Date(), TelefonesUsuario::setDataUltimaAtualizacao);
            mapper.map(src -> usuario, TelefonesUsuario::setUsuario);
            mapper.map(src -> telefone, TelefonesUsuario::setTelefone);
            mapper.<UUID>map(src -> usuario.getCodUsuario(), (telefonesUsuario, cdUsr) -> telefonesUsuario.getTelefonesUsuarioId().setUsuarioId(cdUsr));
            mapper.<Long>map(src -> telefone.getCodigoCadastroTelefone(), (telefonesUsuario, cdCadTel) -> telefonesUsuario.getTelefonesUsuarioId().setTelefoneId(cdCadTel));
        }).map(telefonesUsuarioForm);
    }
}
