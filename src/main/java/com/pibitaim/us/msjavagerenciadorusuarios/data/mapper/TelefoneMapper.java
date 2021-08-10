package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.TelefoneDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.TelefoneForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class TelefoneMapper implements MapperDTO<Telefone, TelefoneDTO>, MapperForm<Telefone, TelefoneForm> {

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public TelefoneDTO converteParaDTO(Telefone telefone) {
        return mapper.typeMap(Telefone.class, TelefoneDTO.class).map(telefone);
    }

    @Override
    public Page<TelefoneDTO> converteParaDTO(Page<Telefone> telefones) {
        return telefones.map(new Function<Telefone, TelefoneDTO>(){
            @Override
            public TelefoneDTO apply(Telefone telefone) {
                return mapper.map(telefone, TelefoneDTO.class);
            }
        });
    }

    @Override
    public Telefone converteParaEntity(TelefoneForm telefoneForm) {
        return mapper.typeMap(TelefoneForm.class, Telefone.class).addMappings(mapper -> {
            mapper.map(src -> new Date(), Telefone::setDataUltimaAtualizacao);
        }).map(telefoneForm);
    }

    public Telefone converteParaEntity(TelefoneForm telefoneForm, Long codCadastroTelefone, List<TelefonesUsuario> listTelefonesUsuario) {
        return mapper.typeMap(TelefoneForm.class, Telefone.class).addMappings(mapper -> {
            mapper.map(src -> new Date(), Telefone::setDataUltimaAtualizacao);
            mapper.map(src -> codCadastroTelefone, Telefone::setCodigoCadastroTelefone);
            mapper.map(src -> listTelefonesUsuario, Telefone::setTelefonesUsuario);
        }).map(telefoneForm);
    }
}
