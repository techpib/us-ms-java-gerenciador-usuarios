package com.pibitaim.us.msjavagerenciadorusuarios.controller;

import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.EnderecoUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.PerfilUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.TelefoneUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.controller.utils.UsuarioUtils;
import com.pibitaim.us.msjavagerenciadorusuarios.data.dto.UsuarioDTO;
import com.pibitaim.us.msjavagerenciadorusuarios.data.form.*;
import com.pibitaim.us.msjavagerenciadorusuarios.data.mapper.UsuarioMapper;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.*;
import com.pibitaim.us.msjavagerenciadorusuarios.utils.EncoderMD5;
import com.pibitaim.us.msjavagerenciadorusuarios.utils.SenhaInicial;
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
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping(value = "/us-gerenciador-usuarios/usuario")
public class UsuarioController {

    private static final Logger logger = Logger.getLogger(String.valueOf(UsuarioController.class));

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecosUsuarioService enderecosUsuarioService;

    @Autowired
    private TelefonesUsuarioService telefonesUsuarioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    @Cacheable(value = "listaUsuarios")
    public Page<UsuarioDTO> findAll(@PageableDefault(sort = "cpfCnpj", direction = Sort.Direction.ASC, page = 0, size = 10)Pageable paginacao){
        return usuarioMapper.converteParaDTO(usuarioService.findAll(paginacao));
    }

    @GetMapping("/{cpfCnpj}")
    public ResponseEntity<UsuarioDTO> findByCpfCnpj(@PathVariable Long cpfCnpj){
        Optional<Usuario> usuario = usuarioService.findByCpfCnpj(cpfCnpj);
        if (usuario.isPresent()){
            return ResponseEntity.ok(usuarioMapper.converteParaDTO(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaUsuarios", allEntries = true)
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioFormPerfil usuarioFormPerfil) throws Exception {
        if(UsuarioUtils.usuarioExiste(usuarioService, usuarioFormPerfil.getCpfCnpj()) ||
                UsuarioUtils.emailCadastrado(usuarioService, usuarioFormPerfil.getEmailUsuario()) ||
                !PerfilUtils.perfisExistem(perfilService, usuarioFormPerfil.getPerfisUsuario())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String senhaInicial = new SenhaInicial().geraSenhaInicial();
        Usuario usuario = usuarioService.save(usuarioMapper.converteParaEntity(usuarioFormPerfil, senhaInicial));

        usuarioFormPerfil.getPerfisUsuario().forEach(perfilDTO -> {
            usuarioService.savePerfil(usuario.getCodUsuario().toString(), perfilDTO);
        });

        return new ResponseEntity<UsuarioDTO>(usuarioMapper.converteParaDTO(usuarioService.findByCpfCnpj(usuario.getCpfCnpj()).get()), HttpStatus.CREATED);

    }

    @PutMapping("/{cpfCnpj}")
    @Transactional
    @CacheEvict(value = "listaUsuarios", allEntries = true)
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long cpfCnpj, @RequestBody @Valid UsuarioForm usuarioForm){
        if(!UsuarioUtils.usuarioExiste(usuarioService, cpfCnpj)){
            return ResponseEntity.notFound().build();
        }
        if(UsuarioUtils.emailCadastradoOutrosUsuarios(usuarioService, usuarioForm.getEmailUsuario(), cpfCnpj) ||
                (usuarioForm.getCpfCnpj().compareTo(cpfCnpj) != 0 && UsuarioUtils.usuarioExiste(usuarioService, usuarioForm.getCpfCnpj()))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        usuarioService.update(usuarioForm, cpfCnpj);
        return new ResponseEntity<UsuarioDTO>(usuarioMapper.converteParaDTO(usuarioService.findByCpfCnpj(usuarioForm.getCpfCnpj()).get()), HttpStatus.OK);
    }

    @PatchMapping("/alteraSenha/{cpfCnpj}")
    @Transactional
    @CacheEvict(value = "listaUsuarios", allEntries = true)
    public ResponseEntity alteraSenha(@PathVariable Long cpfCnpj, @RequestBody @Valid UsuarioSenhaForm usuarioSenhaForm) throws NoSuchAlgorithmException {
        if(validaUsuarioESenha(cpfCnpj, usuarioSenhaForm.getSenhaAtual())){
            usuarioService.updateSenha(cpfCnpj, usuarioSenhaForm.getNovaSenha());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/alterarPerfis/{cpfCnpj}")
    @Transactional
    @CacheEvict(value = "listaUsuarios", allEntries = true)
    public ResponseEntity alterarPerfis(@PathVariable Long cpfCnpj, @RequestBody @Valid PerfisUsuarioForm perfisUsuarioForm) {
        if(UsuarioUtils.usuarioExiste(usuarioService, cpfCnpj)){
            String codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpj).get().toString();
            if(podeAlterarPerfis(perfisUsuarioForm, codUsuario)){
                perfisUsuarioForm.getPerfisUsuarioNovo().forEach(perfil -> {
                    usuarioService.updatePerfis(codUsuario, perfisUsuarioForm.getPerfisUsuarioAtual().get(0), perfil);
                    perfisUsuarioForm.getPerfisUsuarioAtual().remove(0);
                });
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cpfCnpj}")
    @Transactional
    @CacheEvict(value = "listaUsuarios", allEntries = true)
    public ResponseEntity delete(@PathVariable Long cpfCnpj){
        Optional<UUID> codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpj);
        if (codUsuario.isPresent()){
            Optional<List<EnderecosUsuario>> enderecosUsuarios = EnderecoUtils.findCodCadastroEnderecoByCodUsuario(enderecosUsuarioService, codUsuario.get().toString());
            Optional<List<TelefonesUsuario>> telefonesUsuarios = TelefoneUtils.findCodCadastroTelefoneByCodUsuario(telefonesUsuarioService, codUsuario.get().toString());
            usuarioService.deleteByCpfCnpj(cpfCnpj);
            validaEnderecosSemRelacionamento(enderecosUsuarios);
            validaTelefonesSemRelacionamento(telefonesUsuarios);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletaPerfil/{cpfCnpj}/{perfilId}")
    @Transactional
    @CacheEvict(value = "listaUsuarios", allEntries = true)
    public ResponseEntity deletaPerfis(@PathVariable Long cpfCnpj, @PathVariable Long perfilId){
        Optional<UUID> codUsuario = UsuarioUtils.findCodUsuarioByCpfCnpj(usuarioService, cpfCnpj);
        if(codUsuario.isPresent() && PerfilUtils.perfilExiste(perfilService, perfilId) &&
                usuarioService.usuarioPossuiPerfil(codUsuario.get().toString(), perfilId)){
            usuarioService.deletePerfil(codUsuario.get().toString(), perfilId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean validaUsuarioESenha(Long cpfCnpj, String senhaAtual) throws NoSuchAlgorithmException {
        try{
            senhaAtual = new EncoderMD5().encodeToMD5(senhaAtual);
            return usuarioService.findByCpfCnpjAndSenha(cpfCnpj, senhaAtual).isPresent();
        }catch (NoSuchAlgorithmException | NullPointerException exception) {
            throw new NoSuchAlgorithmException("Erro de validações internas");
        }
    }

    private void validaEnderecosSemRelacionamento(Optional<List<EnderecosUsuario>> enderecosUsuarios){

        if(enderecosUsuarios.isPresent()){
            enderecosUsuarios.get().forEach(endereco -> {
                if(EnderecoUtils.isEnderecoSemRelacionamento(enderecosUsuarioService, endereco.getEnderecosUsuarioId().getEnderecoId())){
                    enderecoService.deleteByCodigoCadastroEndereco(endereco.getEnderecosUsuarioId().getEnderecoId());
                }
            });
        }

    }

    private void validaTelefonesSemRelacionamento(Optional<List<TelefonesUsuario>> telefonesUsuarios){

        if(telefonesUsuarios.isPresent()){
            telefonesUsuarios.get().forEach(telefone -> {
                if(TelefoneUtils.isTelefoneSemRelacionamento(telefonesUsuarioService, telefone.getTelefonesUsuarioId().getTelefoneId())){
                    telefoneService.deleteByCodigoCadastroTelefone(telefone.getTelefonesUsuarioId().getTelefoneId());
                }
            });
        }

    }

    private boolean podeAlterarPerfis(PerfisUsuarioForm perfisUsuarioForm, String codUsuario){
        return PerfilUtils.perfisExistem(perfilService, perfisUsuarioForm.getPerfisUsuarioNovo()) &&
                UsuarioUtils.usuarioPossuiPerfis(usuarioService, codUsuario, perfisUsuarioForm.getPerfisUsuarioAtual()) &&
                perfisUsuarioForm.getPerfisUsuarioAtual().size() == perfisUsuarioForm.getPerfisUsuarioNovo().size();
    }

}
