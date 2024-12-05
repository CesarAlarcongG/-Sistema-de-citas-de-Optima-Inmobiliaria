package com.optima.inmobiliaria.citas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CitaRequestDto {

    private Long clienteId;

    @NotNull(message = "La fecha de la cita es obligatoria")
    private Date fechaCita;

    @NotBlank(message = "El proyecto es obligatorio")
    private String proyecto;

    @NotBlank(message = "La consulta es obligatoria")
    private String consulta;


    // Getters y Setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
}

