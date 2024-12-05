package com.optima.inmobiliaria.citas.persistence.repositories;

import com.optima.inmobiliaria.citas.persistence.models.Cita;
import com.optima.inmobiliaria.citas.persistence.models.enums.EstadoCitaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByEstado(EstadoCitaEnum estadoCitaEnum);
    Cita findByIdAndEstado(Long id, EstadoCitaEnum estadoCitaEnum);
}
