package com.pibitaim.us.msjavagerenciadorusuarios.repository;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.EnderecosUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnderecosUsuarioRepository extends JpaRepository<EnderecosUsuario, EnderecosUsuarioId> {

    @Query(value = "SELECT COUNT (*) FROM TBUS005_END_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Integer qtdEnderecosPorCodUsuario(@Param("codUsuario") String codUsuario);

    @Query(value = "SELECT NIV_PRIO FROM TBUS005_END_USU " +
            "WHERE COD_IDE_USU = :codUsuario " +
            "ORDER BY NIV_PRIO DESC LIMIT 1", nativeQuery = true)
    Integer getUltimoNivelPrioridade(@Param("codUsuario") String codUsuario);

    @Query(value = "SELECT * FROM TBUS005_END_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Optional<List<EnderecosUsuario>> existeEnderecosCadastradosParaCodigoUsuario(@Param("codUsuario") String codUsuario);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE TBUS005_END_USU " +
            "SET END_PRI = FALSE " +
            "WHERE COD_IDE_USU = :codUsuario " +
            "  AND END_PRI = TRUE", nativeQuery = true)
    void atualizaEnderecosPrincipais(@Param("codUsuario") String codUsuario);


    @Query(value = "SELECT * FROM TBUS005_END_USU " +
            "WHERE COD_CAD_END = :codCadastroEndereco", nativeQuery = true)
    Optional<List<EnderecosUsuario>> findByEnderecoCodCadastroEndereco(@Param("codCadastroEndereco") Long codCadastroEndereco);

    @Query(value = "SELECT * FROM TBUS005_END_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Optional<List<EnderecosUsuario>> findCodCadastroEnderecoByCodUsuario(@Param("codUsuario") String codusuario);

}
