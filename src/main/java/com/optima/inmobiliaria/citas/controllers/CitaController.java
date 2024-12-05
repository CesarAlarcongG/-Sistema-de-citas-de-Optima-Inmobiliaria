package com.optima.inmobiliaria.citas.controllers;

import com.optima.inmobiliaria.citas.dto.AsignarCitaDto;
import com.optima.inmobiliaria.citas.dto.CitaRequestDto;
import com.optima.inmobiliaria.citas.persistence.models.Cita;
import com.optima.inmobiliaria.citas.services.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cita")
@Validated
public class CitaController {

    @Autowired
    private CitaService citaService;

//Dirigido para el Cliente --
    // Crear cita -
    @PostMapping("/crear")
    public ResponseEntity<Cita> crearCita(@Valid @RequestBody CitaRequestDto citaRequest) {
        Cita cita = citaService.crearCita(citaRequest.getClienteId(), citaRequest.getFechaCita(), citaRequest.getProyecto(), citaRequest.getConsulta());
        return ResponseEntity.status(HttpStatus.CREATED).body(cita);
    }

//Dirigido para el Admiinistrador --
    // Asignar cita a agente
    @PostMapping("/asignar")
    public ResponseEntity<Cita> asignarCita(@RequestBody AsignarCitaDto asignarCitaDto) {
        Cita cita = citaService.asignarCita(asignarCitaDto);
        return ResponseEntity.ok(cita);
    }

    // Reabrir cita
    @PostMapping("/reabrir/{citaId}")
    public ResponseEntity<Cita> reabrirCita(@PathVariable Long citaId) {
        Cita cita = citaService.reabrirCita(citaId);
        return ResponseEntity.ok(cita);
    }

    //Obtener citas por estado
    @GetMapping("/lista/{estado}")
    public List<Cita> obtenerCitaPorEstado(@PathVariable String estado){
        String estadoUpper = estado.toUpperCase();
        return citaService.obtenerCitaPorEstado(estadoUpper);
    }

//Dirigido para el Agente
    // Completar cita
    @PostMapping("/completar/{citaId}")
    public ResponseEntity<Cita> completarCita(@PathVariable Long citaId) {
        Cita cita = citaService.completarCita(citaId);
        return ResponseEntity.ok(cita);
    }




}

