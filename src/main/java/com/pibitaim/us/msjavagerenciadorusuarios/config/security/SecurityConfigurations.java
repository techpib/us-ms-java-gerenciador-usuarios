package com.pibitaim.us.msjavagerenciadorusuarios.config.security;


import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPapel;
import com.pibitaim.us.msjavagerenciadorusuarios.entity.enums.EnumPermissao;
import com.pibitaim.us.msjavagerenciadorusuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //Configuracoes de autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
    }

    //Configuracoes de Autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/us-gerenciador-usuarios/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/us-gerenciador-usuarios/**").hasRole(EnumPapel.ADMIN.getPapel())
                .antMatchers(HttpMethod.GET, "/us-gerenciador-usuarios/**").hasAuthority(EnumPermissao.LEITURA.getPermissao())
                .antMatchers(HttpMethod.POST, "/us-gerenciador-usuarios/**").hasAnyAuthority(EnumPermissao.ESCRITA.getPermissao(), EnumPermissao.ATUALIZACAO.getPermissao())
                .antMatchers(HttpMethod.PUT, "/us-gerenciador-usuarios/**").hasAuthority(EnumPermissao.ATUALIZACAO.getPermissao())
                .antMatchers(HttpMethod.PATCH, "/us-gerenciador-usuarios/**").hasAuthority(EnumPermissao.ATUALIZACAO.getPermissao())
                .antMatchers(HttpMethod.DELETE, "/us-gerenciador-usuarios/**").hasAuthority(EnumPermissao.DELECAO.getPermissao())
                .antMatchers(HttpMethod.GET, "/actuator").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().sameOrigin()
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configuracoes de recursos estaticos(js, css, img, etc.)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

}