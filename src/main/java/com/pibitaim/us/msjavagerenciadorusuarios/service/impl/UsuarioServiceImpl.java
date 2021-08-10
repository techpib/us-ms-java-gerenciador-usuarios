package com.pibitaim.us.msjavagerenciadorusuarios.service.impl;

import com.pibitaim.us.msjavagerenciadorusuarios.data.form.UsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.UsuarioRepository;
import com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces.UsuarioService;
import com.pibitaim.us.msjavagerenciadorusuarios.utils.EncoderMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<UUID> findCodUsuarioByCpfCnpj(Long cpfCnpj) {
        return usuarioRepository.findCodUsuarioByCpfCnpj(cpfCnpj);
    }

    @Override
    public Page<Usuario> findAll(Pageable paginacao) {
        return usuarioRepository.findAll(paginacao);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void savePerfil(String codUsuario, Long perfilId) {
        usuarioRepository.savePerfil(codUsuario, perfilId);
    }

    @Override
    public Optional<Usuario> findByCpfCnpj(Long cpfCnpj) {
        return usuarioRepository.findByCpfCnpj(cpfCnpj);
    }

    @Override
    public Integer qtdEmailCadastrado(String emailUsuario) {
        return usuarioRepository.qtdEmailCadastrado(emailUsuario);
    }

    @Override
    public Integer qtdEmailCadastradoOutrosUsuarios(String emailUsuario, Long cpfCnpj) {
        return usuarioRepository.qtdEmailCadastradoOutrosUsuarios(emailUsuario, cpfCnpj);
    }


    @Override
    public void update(UsuarioForm usuarioForm, Long cpfCnpjOriginal) {
            usuarioRepository.update(usuarioForm.getCpfCnpj(),
                                        usuarioForm.getDataBatismo(),
                                        usuarioForm.getDataNascimento(),
                                        usuarioForm.getEstadoCivil().toString(),
                                        usuarioForm.getSexoUsuario().toString(),
                                        usuarioForm.getTipoPessoa().toString(),
                                        usuarioForm.getIndicadorMembresia(),
                                        usuarioForm.getNomeUsuario(),
                                        cpfCnpjOriginal,
                                        usuarioForm.getEmailUsuario());
    }

    @Override
    public void deleteByCpfCnpj(Long cpfCnpj) {
        usuarioRepository.deleteByCpfCnpj(cpfCnpj);
    }

    @Override
    public Optional<Usuario> findByCpfCnpjAndSenha(Long cpfCnpj, String senhaAtual) {
        return usuarioRepository.findByCpfCnpjAndSenha(cpfCnpj, senhaAtual);
    }

    @Override
    public void updateSenha(Long cpfCnpj, String novaSenha) throws NoSuchAlgorithmException {
        try {
            novaSenha = new EncoderMD5().encodeToMD5(novaSenha);
            usuarioRepository.updateSenha(cpfCnpj, novaSenha, new Date());
        } catch (NoSuchAlgorithmException exception) {
            throw new NoSuchAlgorithmException("Erro inesperado ao salvar o dado");
        }
    }

    @Override
    public void updatePerfis(String codUsuario, Long atualPerfilId, Long novoPerfilId) {
        usuarioRepository.updatePerfil(codUsuario, atualPerfilId, novoPerfilId);
    }

    @Override
    public Page<Usuario> findByEnderecoId(Pageable paginacao, Long enderecoId) {
        return usuarioRepository.findByEnderecoId(paginacao, enderecoId);
    }

    @Override
    public Page<Usuario> findByTelefoneId(Pageable paginacao, Long telefoneId) {
        return usuarioRepository.findByTelefoneId(paginacao, telefoneId);
    }

    @Override
    public Optional<Page<Usuario>> findByPerfilId(Pageable paginacao, Long perfilId) {
        return usuarioRepository.findByPerfilId(paginacao, perfilId);
    }

    @Override
    public boolean existsUsuarioByPerfilId(Long perfilId) {
        return usuarioRepository.findQtdUsuarioByPerfilId(perfilId) > 0 ? true : false;
    }

    @Override
    public boolean usuarioPossuiPerfil(String codUsuario, Long perfilId) {
        return usuarioRepository.findByCodUsuarioAndPerfilId(codUsuario, perfilId).isPresent();
    }

    @Override
    public void deletePerfil(String codUsuario, Long perfilId) {
        usuarioRepository.deletePerfil(codUsuario, perfilId);
    }

}
