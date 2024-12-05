package com.optima.inmobiliaria.citas.persistence.models;

import com.optima.inmobiliaria.citas.persistence.models.enums.EstadoCitaEnum;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.Date;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    @ManyToOne
    private Usuario cliente;

    @ManyToOne
    private Usuario agente;

    @NotNull(message = "La fecha de la cita es obligatoria")
    private Date fechaCita;

    @NotBlank(message = "El proyecto es obligatorio")
    private String proyecto;

    @NotBlank(message = "La consulta es obligatoria")
    private String consulta;

    @Enumerated(EnumType.STRING)
    private EstadoCitaEnum estado;

    private Date fechaAsignacion;
    private Date fechaCierre;


    //Constructores ----
    public Cita(Long id, Usuario cliente, Usuario agente, Date fechaCita, Date fechaCierre, Date fechaAsignacion, EstadoCitaEnum estado, String consulta, String proyecto) {
        this.idCita = id;
        this.cliente = cliente;
        this.agente = agente;
        this.fechaCita = fechaCita;
        this.fechaCierre = fechaCierre;
        this.fechaAsignacion = fechaAsignacion;
        this.estado = estado;
        this.consulta = consulta;
        this.proyecto = proyecto;
    }
    public Cita() {
    }

    //Geters y Setters ----
    public Long getId() {
        return idCita;
    }

    public void setId(Long id) {
        this.idCita = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getAgente() {
        return agente;
    }

    public void setAgente(Usuario agente) {
        this.agente = agente;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public EstadoCitaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCitaEnum estado) {
        this.estado = estado;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
