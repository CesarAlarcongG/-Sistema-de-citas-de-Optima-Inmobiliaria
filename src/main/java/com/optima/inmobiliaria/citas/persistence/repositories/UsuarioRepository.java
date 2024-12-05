package com.optima.inmobiliaria.citas.persistence.repositories;

import com.optima.inmobiliaria.citas.persistence.models.Rol;
import com.optima.inmobiliaria.citas.persistence.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findAllByRol(Rol rol);
}
