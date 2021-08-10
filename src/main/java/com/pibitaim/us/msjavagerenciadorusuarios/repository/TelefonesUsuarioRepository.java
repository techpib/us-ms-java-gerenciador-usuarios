package com.pibitaim.us.msjavagerenciadorusuarios.repository;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.TelefonesUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TelefonesUsuarioRepository extends JpaRepository<TelefonesUsuario, TelefonesUsuarioId> {

    @Query(value = "SELECT * FROM TBUS006_TEL_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Optional<List<TelefonesUsuario>> existeTelefonesCadastradosParaCodUsuario(@Param("codUsuario") String codUsuario);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE TBUS006_TEL_USU " +
            "SET TEL_PRI = FALSE " +
            "WHERE COD_IDE_USU = :codUsuario " +
            "  AND TEL_PRI = TRUE", nativeQuery = true)
    void atualizaTelefonesPrincipais(@Param("codUsuario") String codUsuario);

    @Query(value = "SELECT COUNT (*) FROM TBUS006_TEL_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Integer qtdTelefonesPorCodUsuario(@Param("codUsuario") String codUsuario);

    @Query(value = "SELECT COUNT (*) FROM TBUS006_TEL_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Integer getUltimoNivelPrioridade(@Param("codUsuario") String codUsuario);

    @Query(value = "SELECT * FROM TBUS006_TEL_USU " +
            "WHERE COD_CAD_TEL = :codCadastroTelefone", nativeQuery = true)
    Optional<List<TelefonesUsuario>> findByTelefoneCodCadastroTelefone(@Param("codCadastroTelefone") Long codCadastroTelefone);

    @Query(value = "SELECT * FROM TBUS006_TEL_USU " +
            "WHERE COD_IDE_USU = :codUsuario", nativeQuery = true)
    Optional<List<TelefonesUsuario>> findCodCadastroTelefoneByCodUsuario(@Param("codUsuario") String codUsuario);
}
