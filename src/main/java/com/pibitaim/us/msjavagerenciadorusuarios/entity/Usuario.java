package com.pibitaim.us.msjavagerenciadorusuarios.entity;

import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumEstadoCivil;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumSexoUsuario;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumTipoPessoa;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Data
@Entity(name = "TBUS001_CAD_UNI_USU")
public class Usuario implements UserDetails {

    private static final String ROLE_SUFIX = "ROLE_";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    @Column(name = "COD_IDE_USU")
    private UUID codUsuario;

    @NotNull
    @Column(columnDefinition = "BIGINT(14)", name = "CPF_CNPJ")
    private Long cpfCnpj;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)", name = "NOM_USU")
    private String nomeUsuario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "CHAR(01)", name = "TIP_PES")
    private EnumTipoPessoa enumTipoPessoa;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "CHAR(01)", name = "SEX_USU")
    private EnumSexoUsuario enumSexoUsuario;

    @NotNull
    @Column(columnDefinition = "DATE", name = "DAT_NSC")
    private LocalDate dataNascimento;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "CHAR(01)", name = "EST_CIV")
    private EnumEstadoCivil enumEstadoCivil;

    @NotNull
    @Column(name = "IND_MEM")
    private Boolean indicadorMembresia;

    @Column(columnDefinition = "DATE", name = "DAT_BAT")
    private LocalDate dataBatismo;

    @NotNull
    @Column(name = "DAT_ULT_ATU")
    private Date dataUltimaAtualizacao;

    @NotNull
    @NotBlank
    @Column(name = "EMAIL_USU")
    private String emailUsuario;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)", name = "SEN_ACE_USU")
    private String senhaAcessoUsuario;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EnderecosUsuario> enderecosUsuario;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TelefonesUsuario> telefonesUsuario;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "TBUS008_PER_USU",
            joinColumns = @JoinColumn(name = "COD_IDE_USU"),
            inverseJoinColumns = @JoinColumn(name = "COD_CAD_PER")
    )
    private List<Perfil> perfisUsuarios;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        perfisUsuarios.forEach(perfilUsuario -> {
            authorities.add(new SimpleGrantedAuthority(perfilUsuario.getPermissao().getPermissao()));
            authorities.add(new SimpleGrantedAuthority(ROLE_SUFIX.concat(perfilUsuario.getPapel().getPapel())));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.senhaAcessoUsuario;
    }

    @Override
    public String getUsername() {
        return this.cpfCnpj.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codUsuario=" + codUsuario +
                ", cpfCnpj=" + cpfCnpj +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", enumTipoPessoa=" + enumTipoPessoa +
                ", enumSexoUsuario=" + enumSexoUsuario +
                ", dataNascimento=" + dataNascimento +
                ", enumEstadoCivil=" + enumEstadoCivil +
                ", indicadorMembresia=" + indicadorMembresia +
                ", dataBatismo=" + dataBatismo +
                ", dataUltimaAtualizacao=" + dataUltimaAtualizacao +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", senhaAcessoUsuario='" + senhaAcessoUsuario + '\'' +
                ", enderecosUsuario=" + enderecosUsuario +
                ", telefonesUsuario=" + telefonesUsuario +
                ", perfisUsuarios=" + perfisUsuarios +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getCodUsuario().equals(usuario.getCodUsuario()) &&
                getCpfCnpj().equals(usuario.getCpfCnpj()) &&
                getNomeUsuario().equals(usuario.getNomeUsuario()) &&
                getEnumTipoPessoa() == usuario.getEnumTipoPessoa() &&
                getEnumSexoUsuario() == usuario.getEnumSexoUsuario() &&
                getDataNascimento().equals(usuario.getDataNascimento()) &&
                getEnumEstadoCivil() == usuario.getEnumEstadoCivil() &&
                Objects.equals(getIndicadorMembresia(), usuario.getIndicadorMembresia()) &&
                Objects.equals(getDataBatismo(), usuario.getDataBatismo()) &&
                getDataUltimaAtualizacao().equals(usuario.getDataUltimaAtualizacao()) &&
                getEmailUsuario().equals(usuario.getEmailUsuario()) &&
                getSenhaAcessoUsuario().equals(usuario.getSenhaAcessoUsuario()) &&
                Objects.equals(getEnderecosUsuario(), usuario.getEnderecosUsuario()) &&
                Objects.equals(getTelefonesUsuario(), usuario.getTelefonesUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodUsuario(), getCpfCnpj(), getNomeUsuario(), getEnumTipoPessoa(), getEnumSexoUsuario(), getDataNascimento(), getEnumEstadoCivil(), getIndicadorMembresia(), getDataBatismo(), getDataUltimaAtualizacao(), getEmailUsuario(), getSenhaAcessoUsuario(), getEnderecosUsuario(), getTelefonesUsuario());
    }

}
