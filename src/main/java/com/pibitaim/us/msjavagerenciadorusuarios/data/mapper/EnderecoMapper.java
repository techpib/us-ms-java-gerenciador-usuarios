package com.pibitaim.us.msjavagerenciadorusuarios.data.mapper;

import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.EnderecoDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.EnderecoAtributosForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.EnderecoForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.interfaces.MapperForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Endereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class EnderecoMapper implements MapperDTO<Endereco, EnderecoDTO>, MapperForm<Endereco, EnderecoForm> {

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public EnderecoDTO converteParaDTO(Endereco e) {
        return mapper.map(e, EnderecoDTO.class);
    }

    @Override
    public Page<EnderecoDTO> converteParaDTO(Page<Endereco> enderecos) {
        return enderecos.map(new Function<Endereco, EnderecoDTO>(){
            @Override
            public EnderecoDTO apply(Endereco endereco) {
                return mapper.map(endereco, EnderecoDTO.class);
            }
        });
    }

    @Override
    public Endereco converteParaEntity(EnderecoForm enderecoForm) {
        return mapper.typeMap(EnderecoForm.class, Endereco.class).addMappings(mapper -> {
            mapper.map(src -> new Date(), Endereco::setDataUltimaAtualizacao);
            mapper.map(src -> Integer.parseInt(enderecoForm.getCepEndereco()), Endereco::setCepEndereco);
        }).map(enderecoForm);
    }

    public Endereco converteParaEntity(EnderecoAtributosForm enderecoAtributosForm, Long codigoCadastroEndereco, List<EnderecosUsuario> listEnderecosUsuario) {
        return mapper.typeMap(EnderecoAtributosForm.class, Endereco.class).addMappings(mapper -> {
            mapper.map(src -> new Date(), Endereco::setDataUltimaAtualizacao);
            mapper.map(src -> Integer.parseInt(enderecoAtributosForm.getCepEndereco()), Endereco::setCepEndereco);
            mapper.map(src -> codigoCadastroEndereco, Endereco::setCodigoCadastroEndereco);
            mapper.map(src -> listEnderecosUsuario, Endereco::setEnderecosUsuario);
        }).map(enderecoAtributosForm);
    }
}
