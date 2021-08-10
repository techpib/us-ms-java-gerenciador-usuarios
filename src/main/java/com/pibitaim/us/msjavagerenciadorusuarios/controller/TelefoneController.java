package com.pibitaim.us.msjavagerenciadorusuarios.controller;

import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.TelefoneUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.UsuarioUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.TelefoneDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.UsuarioDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.TelefoneForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.TelefonesUsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.TelefoneMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.TelefonesUsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.UsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.TelefoneService;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.TelefonesUsuarioService;
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
@RequestMapping(value = "/us-gerenciador-usuarios/telefone")
public class TelefoneController {

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TelefonesUsuarioService telefonesUsuarioService;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private TelefonesUsuarioMapper telefonesUsuarioMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    @Cacheable(value = "listaTelefones")
    public Page<TelefoneDTO> findAll(@PageableDefault(sort = "telefoneNumero", direction = Sort.Direction.ASC, page = 0, size = 10)Pageable paginacao){
        return telefoneMapper.converteParaDTO(telefoneService.findAll(paginacao)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelefoneDTO> findById(@PathVariable Long id){
        Optional<Telefone> telefone = telefoneService.findById(id);
        return telefone.isPresent() ? new ResponseEntity<TelefoneDTO>(telefoneMapper.converteParaDTO(telefone.get()), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @GetMapping("/telefonesUsuario/{usuarioCpfCnpj}")
    public ResponseEntity<Page<TelefoneDTO>> findByUsuarioId(@PathVariable Long usuarioCpfCnpj, @PageableDefault(sort = "TEL_NUM", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao){
        if(!UsuarioUtils.usuarioExiste(usuarioService, usuarioCpfCnpj)){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Page<TelefoneDTO>>(telefoneMapper.converteParaDTO(telefoneService.findByUsuarioId(paginacao, usuarioCpfCnpj)), HttpStatus.OK);
    }

    @GetMapping("/usuariosTelefone/{telefoneId}")
    public ResponseEntity<Page<UsuarioDTO>> findUsuariosByTelefoneId(@PathVariable Long telefoneId, @PageableDefault(sort = "CPF_CNPJ", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao){
        if(!TelefoneUtils.telefoneExiste(telefoneService, telefoneId)){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Page<UsuarioDTO>>(usuarioMapper.converteParaDTO(usuarioService.findByTelefoneId(paginacao, telefoneId)), HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaTelefones", allEntries = true)
    public ResponseEntity<TelefoneDTO> save(@RequestBody @Valid TelefoneForm telefoneForm){
        Optional<Usuario> usuario = usuarioService.findByCpfCnpj(telefoneForm.getCpfCnpjUsuario());

        if(!usuario.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Telefone telefone = telefoneService.save(telefoneMapper.converteParaEntity(telefoneForm));
        TelefoneDTO telefoneDTO = telefoneMapper.converteParaDTO(telefone);

        TelefonesUsuarioForm telefonesUsuarioForm = new TelefonesUsuarioForm();
        telefonesUsuarioForm.setCodigoCadastroTelefone(telefone.getCodigoCadastroTelefone());
        telefonesUsuarioForm.setCpfCnpjUsuario(telefoneForm.getCpfCnpjUsuario());

        boolean existeTelefonesCadastrados = TelefoneUtils.existeTelefonesCadastradosParaCodUsuario(telefonesUsuarioService, usuario.get().getCodUsuario().toString());

        if(telefoneForm.isTelefonePrincipal() && existeTelefonesCadastrados){
            telefonesUsuarioService.atualizaTelefonesPrincipais(usuario.get().getCodUsuario().toString());
        } else if(!existeTelefonesCadastrados && !telefoneForm.isTelefonePrincipal()){
            telefoneForm.setTelefonePrincipal(true);
        }

        telefonesUsuarioForm.setTelefonePrincipal(telefoneForm.isTelefonePrincipal());

        Integer nivelPrioridade = TelefoneUtils.qtdTelefonesCodUsuario(telefonesUsuarioService, usuario.get().getCodUsuario().toString()) == 0 ? 1
                                : TelefoneUtils.getUltimoNivelPrioridade(telefonesUsuarioService, usuario.get().getCodUsuario().toString()) + 1;

        telefonesUsuarioForm.setNivelPrioridade(nivelPrioridade);

        telefonesUsuarioService.save(telefonesUsuarioMapper.converteParaEntity(telefonesUsuarioForm, usuario.get(), telefone));

        return new ResponseEntity<TelefoneDTO>(telefoneDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{cpfCnpjUsuario}/{id}")
    @Transactional
    @CacheEvict(value = "listaTelefones", allEntries = true)
    public ResponseEntity<TelefoneDTO> update(@PathVariable Long cpfCnpjUsuario, @PathVariable Long id, @RequestBody @Valid TelefoneForm telefoneForm){
        Optional<List<TelefonesUsuario>> listTelefonesUsuario = telefonesUsuarioService.findByTelefoneCodCadastroTelefone(id);
        Optional<UUID> codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpjUsuario);

        if(!TelefoneUtils.telefoneExiste(telefoneService, id) || !UsuarioUtils.usuarioExiste(usuarioService, cpfCnpjUsuario) || !usuarioContemTelefone(codUsuario, listTelefonesUsuario)){
            return ResponseEntity.notFound().build();
        }

        List<TelefonesUsuario> telefonesUsuarios = atualizaTelefonesUsuarioParaCpfCnpj(codUsuario.get(), id, listTelefonesUsuario.get(), telefoneForm.isTelefonePrincipal());

        return new ResponseEntity<TelefoneDTO>(telefoneMapper.converteParaDTO(telefoneService.save(telefoneMapper.converteParaEntity(telefoneForm, id, telefonesUsuarios))), HttpStatus.OK);
    }

    @DeleteMapping("/{cpfCnpjUsuario}/{id}")
    @Transactional
    @CacheEvict(value = "listaTelefones", allEntries = true)
    public ResponseEntity delete(@PathVariable Long cpfCnpjUsuario, @PathVariable Long id){

        Optional<List<TelefonesUsuario>> listTelefonesUsuario = telefonesUsuarioService.findByTelefoneCodCadastroTelefone(id);
        Optional<UUID> codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpjUsuario);

        if(!TelefoneUtils.telefoneExiste(telefoneService, id) || !UsuarioUtils.usuarioExiste(usuarioService, cpfCnpjUsuario) || !usuarioContemTelefone(codUsuario, listTelefonesUsuario)){
            return ResponseEntity.notFound().build();
        }

        telefoneService.deleteByCodigoCadastroTelefone(id);
        return ResponseEntity.ok().build();

    }

    private boolean usuarioContemTelefone(Optional<UUID> codUsuario, Optional<List<TelefonesUsuario>> listTelefonesUsuario){
        if(!codUsuario.isPresent() || !listTelefonesUsuario.isPresent()){
            return false;
        }
        return listTelefonesUsuario.get().stream().filter(telefonesUsuario -> telefonesUsuario.getTelefonesUsuarioId().getUsuarioId().compareTo(codUsuario.get()) == 0).count() > 0 ? true : false;
    }

    private List<TelefonesUsuario> atualizaTelefonesUsuarioParaCpfCnpj(UUID codUsuario, Long telefoneId, List<TelefonesUsuario> listTelefonesUsuario, boolean telefonePrincipal){
        if(telefonePrincipal == true){
            telefonesUsuarioService.atualizaTelefonesPrincipais(codUsuario.toString());
        }

        listTelefonesUsuario.forEach(telefonesUsuario -> {
            if(telefonesUsuario.getTelefonesUsuarioId().getUsuarioId().compareTo(codUsuario) == 0 && telefonesUsuario.getTelefonesUsuarioId().getTelefoneId() == telefoneId){
                telefonesUsuario.setTelefonePrincipal(telefonePrincipal);
            }
        });
        return listTelefonesUsuario;
    }

}
