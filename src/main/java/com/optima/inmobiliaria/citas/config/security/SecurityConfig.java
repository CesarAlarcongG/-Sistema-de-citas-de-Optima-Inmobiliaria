package com.optima.inmobiliaria.citas.config.security;

import com.optima.inmobiliaria.citas.config.security.jwt.JwtFiltro;
import com.optima.inmobiliaria.citas.config.security.jwt.JwtService;

import com.optima.inmobiliaria.citas.persistence.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        JwtFiltro jwtFiltro = new JwtFiltro(jwtService, userDetailsService); // Crear instancia de JwtFiltro manualmente

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest -> authRequest

                        //------------end Point Publicos
                        //Loggin y registro - Usuario
                        .requestMatchers( "/usuario/registro", "/usuario/login", "/usuario/**").permitAll()

                        //------------end Point Privados

                        //Permitidos para el ADMINISTRADOR
                        .requestMatchers( "/usuario/agentes", "/cita/asignar").hasAuthority("ADMINISTRADOR")

                        //Permitidos para el CLIENTE y ADMINISTRADOR
                        .requestMatchers("/cita/crear").hasAnyAuthority("CLIENTE", "ADMINISTRADOR")

                        //Permitidos para el AGENTE y ADMINISTRADOR
                        .requestMatchers("/cita/completar/**").hasAnyAuthority("AGENTE", "ADMINISTRADOR")

                        .anyRequest().authenticated()


                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFiltro, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

}
