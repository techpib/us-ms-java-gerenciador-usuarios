package com.pibitaim.us.msjavagerenciadorusuarios.repository;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query(value = "SELECT * FROM TBUS007_CAD_PER_ACE PER " +
            "INNER JOIN TBUS008_PER_USU PEU " +
            "ON (PER.COD_CAD_PER = PEU.COD_CAD_PER) " +
            "INNER JOIN TBUS001_CAD_UNI_USU USU " +
            "ON (PEU.COD_IDE_USU = USU.COD_IDE_USU) " +
            "WHERE USU.CPF_CNPJ = :usuarioCpfCnpj", nativeQuery = true)
    Page<Perfil> findByUsuarioId(Pageable paginacao, @Param("usuarioCpfCnpj") Long usuarioCpfCnpj);

    @Query(value = "SELECT * FROM TBUS007_CAD_PER_ACE " +
            "WHERE PER_PAP = :papel " +
            "  AND PER_PRM = :permissao", nativeQuery = true)
    Optional<Perfil> existsByPapelPermissao(@Param("papel") String papel, @Param("permissao") String permissao);

}
