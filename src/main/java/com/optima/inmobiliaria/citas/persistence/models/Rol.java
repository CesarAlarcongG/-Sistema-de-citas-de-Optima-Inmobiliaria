package com.optima.inmobiliaria.citas.persistence.models;

import com.optima.inmobiliaria.citas.persistence.models.enums.RolEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RolEnum rol;


    public Rol(Long idRol, RolEnum rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public Rol() {
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public RolEnum getRol() {
        return rol;
    }

    public void setRol(RolEnum rol) {
        this.rol = rol;
    }
}
