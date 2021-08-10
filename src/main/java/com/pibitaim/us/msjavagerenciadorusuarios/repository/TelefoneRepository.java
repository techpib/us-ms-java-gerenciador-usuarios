package com.pibitaim.us.msjavagerenciadorusuarios.repository;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.Telefone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    @Query(value = "SELECT * FROM TBUS003_CAD_TEL_USU TEL " +
            "INNER JOIN TBUS006_TEL_USU TEU " +
            "ON (TEL.COD_CAD_TEL = TEU.COD_CAD_TEL) " +
            "INNER JOIN TBUS001_CAD_UNI_USU USU " +
            "ON (TEU.COD_IDE_USU = USU.COD_IDE_USU) " +
            "WHERE USU.CPF_CNPJ = :usuarioCpfCnpj", nativeQuery = true)
    Page<Telefone> findByUsuarioId(Pageable paginacao, @Param("usuarioCpfCnpj") Long usuarioCpfCnpj);

    void deleteByCodigoCadastroTelefone(Long codigoCadastroTelefone);
}
