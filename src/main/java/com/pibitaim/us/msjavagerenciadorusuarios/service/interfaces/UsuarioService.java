package com.pibitaim.us.msjavagerenciadorusuarios.service.interfaces;

import com.pibitaim.us.msjavagerenciadorusuarios.data.form.UsuarioForm;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    Optional<UUID> findCodUsuarioByCpfCnpj(Long cpfCnpj);

    Page<Usuario> findAll(Pageable paginacao);

    Usuario save(Usuario usuario);

    void savePerfil(String codUsuario, Long perfilId);

    Optional<Usuario> findByCpfCnpj(Long cpfCnpj);

    Integer qtdEmailCadastrado(String emailUsuario);

    Integer qtdEmailCadastradoOutrosUsuarios(String emailUsuario, Long cpfCnpj);

    void update(UsuarioForm usuarioForm, Long cpfCnpjOriginal);

    void deleteByCpfCnpj(Long cpfCnpj);

    Optional<Usuario> findByCpfCnpjAndSenha(Long cpfCnpj, String senhaAtual);

    void updateSenha(Long cpfCnpj, String novaSenha) throws NoSuchAlgorithmException;

    void updatePerfis(String codUsuario, Long atualPerfilId, Long novoPerfilId);

    Page<Usuario> findByEnderecoId(Pageable paginacao, Long enderecoId);

    Page<Usuario> findByTelefoneId(Pageable paginacao, Long telefoneId);

    Optional<Page<Usuario>> findByPerfilId(Pageable paginacao, Long perfilId);

    boolean existsUsuarioByPerfilId(Long perfilId);

    boolean usuarioPossuiPerfil(String codUsuario, Long perfilId);

    void deletePerfil(String codUsuario, Long perfilId);

}
