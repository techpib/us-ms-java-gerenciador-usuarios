package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces;

import org.springframework.data.domain.Page;

public interface MapperDTO<Entity, DTO> {

      DTO converteParaDTO(Entity e);

      Page<DTO> converteParaDTO(Page<Entity> e);

}
