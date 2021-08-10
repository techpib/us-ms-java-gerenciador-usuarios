package com.pibitaim.us.msjavagerenciadorusuarios.controller;

import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.EnderecoUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.UsuarioUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.EnderecoDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.UsuarioDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.EnderecoAtributosForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.EnderecoForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.EnderecosUsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.EnderecoMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.EnderecosUsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.UsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Endereco;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.EnderecoService;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.EnderecosUsuarioService;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/us-gerenciador-usuarios/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecosUsuarioService enderecosUsuarioService;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private EnderecosUsuarioMapper enderecosUsuarioMapper;

    @GetMapping
    @Cacheable(value = "listaEnderecos")
    public Page<EnderecoDTO> findAll(@PageableDefault(sort = "cepEndereco", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao){
        return enderecoMapper.converteParaDTO(enderecoService.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Long id){
        Optional<Endereco> endereco = enderecoService.findById(id);
        return endereco.isPresent() ? new ResponseEntity<EnderecoDTO>(enderecoMapper.converteParaDTO(endereco.get()), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/enderecosUsuario/{usuarioCpfCnpj}")
    public ResponseEntity<Page<EnderecoDTO>> findByUsuarioId(@PathVariable Long usuarioCpfCnpj, @PageableDefault(sort = "CEP_END", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao){
        if (!UsuarioUtils.usuarioExiste(usuarioService, usuarioCpfCnpj)){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Page<EnderecoDTO>>(enderecoMapper.converteParaDTO(enderecoService.findByUsuarioId(paginacao, usuarioCpfCnpj)), HttpStatus.OK);
    }

    @GetMapping("/usuariosEndereco/{enderecoId}")
    public ResponseEntity<Page<UsuarioDTO>> findUsuariosByEnderecoId(@PathVariable Long enderecoId, @PageableDefault(sort = "CPF_CNPJ", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao){
        if(!EnderecoUtils.enderecoExiste(enderecoService, enderecoId)){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Page<UsuarioDTO>>(usuarioMapper.converteParaDTO(usuarioService.findByEnderecoId(paginacao, enderecoId)), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaEnderecos", allEntries = true)
    public ResponseEntity<EnderecoDTO> save(@RequestBody @Valid EnderecoForm enderecoForm){
        Optional<Usuario> usuario = usuarioService.findByCpfCnpj(enderecoForm.getCpfCnpjUsuario());

        if(!usuario.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Endereco endereco = enderecoService.save(enderecoMapper.converteParaEntity(enderecoForm));
        EnderecoDTO enderecoDTO = enderecoMapper.converteParaDTO(endereco);

        EnderecosUsuarioForm enderecosUsuarioForm = new EnderecosUsuarioForm();
        enderecosUsuarioForm.setCodigoCadastroEndereco(endereco.getCodigoCadastroEndereco());
        enderecosUsuarioForm.setCpfCnpjUsuario(enderecoForm.getCpfCnpjUsuario());

        boolean existeEnderecosCadastrados = EnderecoUtils.existeEnderecosCadastradosParaCodUsuario(enderecosUsuarioService, usuario.get().getCodUsuario().toString());

        if(enderecoForm.isEnderecoPrincipal() && existeEnderecosCadastrados){
            enderecosUsuarioService.atualizaEnderecosPrincipais(usuario.get().getCodUsuario().toString());
        } else if(!existeEnderecosCadastrados && !enderecoForm.isEnderecoPrincipal()){
            enderecoForm.setEnderecoPrincipal(true);
        }

        enderecosUsuarioForm.setEnderecoPrincipal(enderecoForm.isEnderecoPrincipal());

        Integer nivelPrioridade = EnderecoUtils.qtdEnderecosCodUsuario(enderecosUsuarioService, usuario.get().getCodUsuario().toString()) == 0 ? 1
                                : EnderecoUtils.getUltimoNivelPrioridade(enderecosUsuarioService, usuario.get().getCodUsuario().toString()) + 1;

        enderecosUsuarioForm.setNivelPrioridade(nivelPrioridade);

        enderecosUsuarioService.save(enderecosUsuarioMapper.converteParaEntity(enderecosUsuarioForm, usuario.get(), endereco));

        return new ResponseEntity<EnderecoDTO>(enderecoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{cpfCnpjUsuario}/{id}")
    @Transactional
    @CacheEvict(value = "listaEnderecos", allEntries = true)
    public ResponseEntity<EnderecoDTO> update(@PathVariable Long cpfCnpjUsuario, @PathVariable Long id, @RequestBody @Valid EnderecoAtributosForm enderecoAtributosForm){
        Optional<List<EnderecosUsuario>> listEnderecosUsuario = enderecosUsuarioService.findByEnderecoCodCadastroEndereco(id);
        Optional<UUID> codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpjUsuario);

        if(!EnderecoUtils.enderecoExiste(enderecoService, id) || !UsuarioUtils.usuarioExiste(usuarioService, cpfCnpjUsuario) || !usuarioContemEndereco(codUsuario, listEnderecosUsuario)){
            return ResponseEntity.notFound().build();
        }

        List<EnderecosUsuario> enderecosUsuarios = atualizaEnderecosUsuarioParaCpfCnpj(codUsuario.get(), id, listEnderecosUsuario.get(), enderecoAtributosForm.isEnderecoPrincipal());

        return new ResponseEntity<EnderecoDTO>(enderecoMapper.converteParaDTO(enderecoService.save(enderecoMapper.converteParaEntity(enderecoAtributosForm, id, enderecosUsuarios))), HttpStatus.OK);

    }

    @DeleteMapping("/{cpfCnpjUsuario}/{id}")
    @Transactional
    @CacheEvict(value = "listaEnderecos", allEntries = true)
    public ResponseEntity delete(@PathVariable Long cpfCnpjUsuario, @PathVariable Long id){

        Optional<List<EnderecosUsuario>> listEnderecosUsuario = enderecosUsuarioService.findByEnderecoCodCadastroEndereco(id);
        Optional<UUID> codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpjUsuario);

        if(!EnderecoUtils.enderecoExiste(enderecoService, id) || !UsuarioUtils.usuarioExiste(usuarioService, cpfCnpjUsuario) || !usuarioContemEndereco(codUsuario, listEnderecosUsuario)){
            return ResponseEntity.notFound().build();
        }

        enderecoService.deleteByCodigoCadastroEndereco(id);
        return ResponseEntity.ok().build();

    }

    private boolean usuarioContemEndereco(Optional<UUID> codUsuario, Optional<List<EnderecosUsuario>> listEnderecosUsuario){
        if(!codUsuario.isPresent() || !listEnderecosUsuario.isPresent()){
            return false;
        }
        return listEnderecosUsuario.get().stream().filter(enderecoUsuario -> enderecoUsuario.getEnderecosUsuarioId().getUsuarioId().compareTo(codUsuario.get()) == 0).count() > 0 ? true : false;
    }

    private List<EnderecosUsuario> atualizaEnderecosUsuarioParaCpfCnpj(UUID codUsuario, Long enderecoId, List<EnderecosUsuario> listEnderecosUsuario, boolean endereoPrincipal){
        if(endereoPrincipal == true){
            enderecosUsuarioService.atualizaEnderecosPrincipais(codUsuario.toString());
        }

        listEnderecosUsuario.forEach(enderecosUsuario -> {
            if(enderecosUsuario.getEnderecosUsuarioId().getUsuarioId().compareTo(codUsuario) == 0 && enderecosUsuario.getEnderecosUsuarioId().getEnderecoId() == enderecoId){
                enderecosUsuario.setEnderecoPrincipal(endereoPrincipal);
            }
        });
        return listEnderecosUsuario;
    }

}
