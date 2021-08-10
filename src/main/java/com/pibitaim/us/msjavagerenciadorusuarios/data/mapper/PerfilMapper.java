package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.PerfilDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.PerfilForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Perfil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PerfilMapper implements MapperDTO<Perfil, PerfilDTO>, MapperForm<Perfil, PerfilForm> {

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public PerfilDTO converteParaDTO(Perfil perfil) {
        return mapper.typeMap(Perfil.class, PerfilDTO.class).map(perfil);
    }

    @Override
    public Page<PerfilDTO> converteParaDTO(Page<Perfil> perfis) {
        return perfis.map(new Function<Perfil, PerfilDTO>(){
            @Override
            public PerfilDTO apply(Perfil perfil) {
                return mapper.map(perfil, PerfilDTO.class);
            }
        });
    }

    @Override
    public Perfil converteParaEntity(PerfilForm perfilForm) {
        return mapper.typeMap(PerfilForm.class, Perfil.class).map(perfilForm);
    }
}
