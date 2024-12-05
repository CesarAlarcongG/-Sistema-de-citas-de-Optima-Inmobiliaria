package com.optima.inmobiliaria.citas.services;

import com.optima.inmobiliaria.citas.persistence.models.Rol;
import com.optima.inmobiliaria.citas.persistence.models.enums.RolEnum;
import com.optima.inmobiliaria.citas.persistence.repositories.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    //Iniciamos los datos del campo Rol
    @PostConstruct
    public void iniciarDatosRol(){
        if (rolRepository.count()== 0){
            Rol rolAgente = new Rol();
            rolAgente.setRol(RolEnum.AGENTE);
            rolRepository.save(rolAgente);

            Rol rolAdmin = new Rol();
            rolAdmin.setRol(RolEnum.ADMINISTRADOR);
            rolRepository.save(rolAdmin);

            Rol rolCliente = new Rol();
            rolCliente.setRol(RolEnum.CLIENTE);
            rolRepository.save(rolCliente);

        }
    }

    public Rol obtenerRol(RolEnum rolEnum){
        return rolRepository.findByRol(rolEnum);
    }


}
