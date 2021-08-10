package com.pibitaim.us.msjavagerenciadorusuarios.repository;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query(value = "SELECT COD_IDE_USU FROM TBUS001_CAD_UNI_USU " +
            "WHERE CPF_CNPJ = :cpfCnpj", nativeQuery = true)
    Optional<UUID> findCodUsuarioByCpfCnpj(@Param("cpfCnpj") Long cpfCnpj);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU " +
            "WHERE CPF_CNPJ = :cpfCnpj", nativeQuery = true)
    Optional<Usuario> findByCpfCnpj(@Param("cpfCnpj") Long cpfCnpj);

    @Query(value = "SELECT COUNT(*) FROM TBUS001_CAD_UNI_USU " +
            "WHERE EMAIL_USU = :emailUsuario", nativeQuery = true)
    Integer qtdEmailCadastrado(@Param("emailUsuario") String emailUsuario);

    @Query(value = "SELECT COUNT(*) FROM TBUS001_CAD_UNI_USU " +
            "WHERE EMAIL_USU = :emailUsuario " +
            "  AND CPF_CNPJ <> :cpfCnpj", nativeQuery = true)
    Integer qtdEmailCadastradoOutrosUsuarios(@Param("emailUsuario") String emailUsuario,
                                             @Param("cpfCnpj") Long cpfCnpj);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Optional<Usuario> findByCodUsuario(@Param("codUsuario") String codUsuario);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE TBUS001_CAD_UNI_USU SET " +
            "CPF_CNPJ = :cpfCnpj, " +
            "DAT_BAT = :dataBatismo, " +
            "DAT_NSC = :dataNascimento, " +
            "DAT_ULT_ATU = CURRENT_TIMESTAMP, " +
            "EST_CIV = :estadoCivil, " +
            "SEX_USU = :sexoUsuario, " +
            "TIP_PES = :tipoPessoa, " +
            "IND_MEM = :indicadorMembresia, " +
            "NOM_USU = :nomeUsuario, " +
            "EMAIL_USU = :emailUsuario " +
            "WHERE CPF_CNPJ = :cpfCnpjOriginal", nativeQuery = true)
    void update(@Param("cpfCnpj") Long cpfCnpj,
                   @Param("dataBatismo") LocalDate dataBatismo,
                   @Param("dataNascimento") LocalDate dataNascimento,
                   @Param("estadoCivil") String estadoCivil,
                   @Param("sexoUsuario") String sexoUsuario,
                   @Param("tipoPessoa") String tipoPessoa,
                   @Param("indicadorMembresia") Boolean indicadorMembresia,
                   @Param("nomeUsuario") String nomeUsuario,
                   @Param("cpfCnpjOriginal") Long cpfCnpjOriginal,
                   @Param("emailUsuario") String emailUsuario);

    void deleteByCpfCnpj(Long cpfCnpj);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU " +
            "WHERE CPF_CNPJ = :cpfCnpj " +
            "  AND SEN_ACE_USU = :senhaAtual", nativeQuery = true)
    Optional<Usuario> findByCpfCnpjAndSenha(@Param("cpfCnpj") Long cpfCnpj, @Param("senhaAtual") String senhaAtual);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE TBUS001_CAD_UNI_USU " +
            "SET SEN_ACE_USU = :novaSenha," +
            "    DAT_ULT_ATU = :dataUltimaAtualizacao " +
            "WHERE CPF_CNPJ = :cpfCnpj", nativeQuery = true)
    void updateSenha(@Param("cpfCnpj") Long cpfCnpj, @Param("novaSenha") String novaSenha, @Param("dataUltimaAtualizacao") Date dataUltimaAtualizacao);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE TBUS008_PER_USU " +
            "SET COD_CAD_PER = :novoPerfilId " +
            "WHERE COD_IDE_USU = :codUsuario " +
            "  AND COD_CAD_PER = :atualPerfilId", nativeQuery = true)
    void updatePerfil(@Param("codUsuario") String codUsuario, @Param("atualPerfilId") Long atualPerfilId, @Param("novoPerfilId") Long novoPerfilId);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU USU " +
            "INNER JOIN TBUS005_END_USU END " +
            "ON (USU.COD_IDE_USU = END.COD_IDE_USU) " +
            "WHERE END.COD_CAD_END = :enderecoId", nativeQuery = true)
    Page<Usuario> findByEnderecoId(Pageable paginacao, @Param("enderecoId") Long enderecoId);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU USU " +
            "INNER JOIN TBUS006_TEL_USU TEL " +
            "ON (USU.COD_IDE_USU = TEL.COD_IDE_USU) " +
            "WHERE TEL.COD_CAD_TEL = :telefoneId", nativeQuery = true)
    Page<Usuario> findByTelefoneId(Pageable paginacao, @Param("telefoneId") Long telefoneId);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU USU " +
            "INNER JOIN TBUS008_PER_USU PER " +
            "ON (USU.COD_IDE_USU = PER.COD_IDE_USU) " +
            "WHERE PER.COD_CAD_PER = :perfilId", nativeQuery = true)
    Optional<Page<Usuario>> findByPerfilId(Pageable paginacao, @Param("perfilId") Long perfilId);

    @Query(value = "SELECT COUNT(*) FROM TBUS008_PER_USU " +
            "WHERE COD_CAD_PER = :perfilId", nativeQuery = true)
    Integer findQtdUsuarioByPerfilId(@Param("perfilId") Long perfilId);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO TBUS008_PER_USU  " +
            "(COD_IDE_USU, COD_CAD_PER) VALUES " +
            "(:codUsuario, :perfilId) ", nativeQuery = true)
    void savePerfil(@Param("codUsuario") String codUsuario, @Param("perfilId") Long perfilId);

    @Query(value = "SELECT * FROM TBUS001_CAD_UNI_USU USU " +
            "INNER JOIN TBUS008_PER_USU PEU " +
            "ON (USU.COD_IDE_USU = PEU.COD_IDE_USU) " +
            "WHERE PEU.COD_IDE_USU = :codUsuario " +
            "  AND PEU.COD_CAD_PER = :perfilId", nativeQuery = true)
    Optional<Usuario> findByCodUsuarioAndPerfilId(@Param("codUsuario") String codUsuario, @Param("perfilId") Long perfilId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM TBUS008_PER_USU " +
            "WHERE COD_IDE_USU = :codUsuario " +
            "  AND COD_CAD_PER = :perfilId", nativeQuery = true)
    void deletePerfil(@Param("codUsuario") String codUsuario, @Param("perfilId") Long perfilId);

}
