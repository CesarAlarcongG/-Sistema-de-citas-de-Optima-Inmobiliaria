package com.optima.inmobiliaria.citas.persistence.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String contrseña;

    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Rol rol;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol != null) {
            return List.of(new SimpleGrantedAuthority(rol.getRol().toString()));

        } else {
            System.out.println("El rol esta nulo");
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return this.contrseña;
    }

    @Override
    public String getUsername() {
        return this.correo;
    }

    //Constructores

    public Usuario(Long id, String nombre, String correo, String contrseña, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrseña = contrseña;
        this.rol = rol;
    }
    public Usuario() {
    }

    //Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrseña() {
        return contrseña;
    }

    public void setContrseña(String contrseña) {
        this.contrseña = contrseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
