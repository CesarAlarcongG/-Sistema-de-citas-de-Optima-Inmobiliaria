package com.optima.inmobiliaria.citas.services;

import com.optima.inmobiliaria.citas.dto.AsignarCitaDto;
import com.optima.inmobiliaria.citas.persistence.models.Cita;
import com.optima.inmobiliaria.citas.persistence.models.Usuario;
import com.optima.inmobiliaria.citas.persistence.models.enums.EstadoCitaEnum;
import com.optima.inmobiliaria.citas.persistence.repositories.CitaRepository;
import com.optima.inmobiliaria.citas.persistence.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear cita
    public Cita crearCita(Long clienteId,Date fechaCita, String proyecto, String consulta) {
        Usuario cliente = usuarioRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setFechaCita(fechaCita);
        cita.setProyecto(proyecto);
        cita.setConsulta(consulta);
        cita.setEstado(EstadoCitaEnum.PENDIENTE);

        return citaRepository.save(cita);
    }

    // Asignar cita a agente
    public Cita asignarCita(AsignarCitaDto asignarCitaDto) {
        Cita cita = citaRepository.findById(asignarCitaDto.getIdCita()).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        Usuario agente = usuarioRepository.findById(asignarCitaDto.getIdAgente()).orElseThrow(() -> new RuntimeException("Agente no encontrado"));


        cita.setAgente(agente);

        cita.setFechaAsignacion(new Date());
        cita.setEstado(EstadoCitaEnum.ASIGNADA);

        return citaRepository.save(cita);
    }

    // Marcar cita como completada
    public Cita completarCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(EstadoCitaEnum.COMPLETADA);
        cita.setFechaCierre(new Date());

        return citaRepository.save(cita);
    }

    // Reabrir una cita
    public Cita reabrirCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setFechaCierre(null);
        cita.setEstado(EstadoCitaEnum.REABIERTA);

        return citaRepository.save(cita);
    }

    //Obtener cita por estado
    public List<Cita> obtenerCitaPorEstado(String estado){
        // Convertir el estado de String a EstadoCitaEnum
        EstadoCitaEnum estadoEnum = EstadoCitaEnum.valueOf(estado.toUpperCase());
        // Buscar las citas con ese estado
        return citaRepository.findByEstado(estadoEnum);

    }
}

