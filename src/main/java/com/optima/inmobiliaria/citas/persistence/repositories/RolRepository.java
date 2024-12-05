package com.optima.inmobiliaria.citas.persistence.repositories;

import com.optima.inmobiliaria.citas.persistence.models.Rol;
import com.optima.inmobiliaria.citas.persistence.models.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByRol(RolEnum rol);
}
