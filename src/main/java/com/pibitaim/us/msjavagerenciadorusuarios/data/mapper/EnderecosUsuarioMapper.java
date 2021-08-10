package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.form.EnderecosUsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperEnderecosUsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Endereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class EnderecosUsuarioMapper implements MapperEnderecosUsuarioMapper<EnderecosUsuario, EnderecosUsuarioForm> {

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public EnderecosUsuario converteParaEntity(EnderecosUsuarioForm enderecosUsuarioForm, Usuario usuario, Endereco endereco) {
        return mapper.typeMap(EnderecosUsuarioForm.class, EnderecosUsuario.class).addMappings(mapper -> {
            mapper.map(src -> new Date(), EnderecosUsuario::setDataUltimaAtualizacao);
            mapper.map(src -> usuario, EnderecosUsuario::setUsuario);
            mapper.map(src -> endereco, EnderecosUsuario::setEndereco);
            mapper.<UUID>map(src -> usuario.getCodUsuario(), (enderecosUsuario, cdUsr) -> enderecosUsuario.getEnderecosUsuarioId().setUsuarioId(cdUsr));
            mapper.<Long>map(src -> endereco.getCodigoCadastroEndereco(), (enderecosUsuario, cdCadEnd) -> enderecosUsuario.getEnderecosUsuarioId().setEnderecoId(cdCadEnd));
        }).map(enderecosUsuarioForm);
    }
}
