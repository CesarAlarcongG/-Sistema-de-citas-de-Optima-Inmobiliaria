package com.optima.inmobiliaria.citas.services;

import com.optima.inmobiliaria.citas.config.security.jwt.JwtService;
import com.optima.inmobiliaria.citas.dto.LoginDto;
import com.optima.inmobiliaria.citas.dto.RegistroDto;
import com.optima.inmobiliaria.citas.persistence.models.Rol;
import com.optima.inmobiliaria.citas.persistence.models.Usuario;
import com.optima.inmobiliaria.citas.persistence.models.enums.RolEnum;
import com.optima.inmobiliaria.citas.persistence.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolService rolService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public boolean validarFormatoCorreo(String correo) {

        String EMAIL_PATTERN = "^[\\w+_.-]+@[\\w.-]+\\.[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (correo == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }

    public boolean validarExistenciaUsuario(String correo){

        return usuarioRepository.findByCorreo(correo).isPresent();

    }

    public String registrarCuenta(RegistroDto registroDto){
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDto.getNombre());
        usuario.setCorreo(registroDto.getCorreo());
        usuario.setContrseña(passwordEncoder.encode(registroDto.getContraseña()));

        if (!registraUsuarioRol(registroDto.getRol().toUpperCase(), usuario)){
            return "Digite bien el rol (AGENTE, CLIENTE, ADMINISTRADOR)";
        }

        usuarioRepository.save(usuario);

        String token = jwtService.getToken(usuario);
        return token;


    }
    private boolean registraUsuarioRol(String rol, Usuario usuario) {
        if (rol == null || rol.isEmpty()) {
            return false;
        }


        Map<String, RolEnum> rolesMap = Map.of(
                "AGENTE", RolEnum.AGENTE,
                "CLIENTE", RolEnum.CLIENTE,
                "ADMINISTRADOR", RolEnum.ADMINISTRADOR
        );

        RolEnum rolEnum = rolesMap.get(rol.toUpperCase());
        if (rolEnum == null) {
            return false;
        }

        Rol rolEntidad = rolService.obtenerRol(rolEnum);
        if (rolEntidad == null) {
            return false;
        }

        usuario.setRol(rolEntidad);
        return true;
    }

    //Métodos para el login
    public String loggin(LoginDto loginDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getCorreo(), loginDto.getContraseña()));
        UserDetails user = usuarioRepository.findByCorreo(loginDto.getCorreo()).orElseThrow();
        String stringToken = jwtService.getToken(user);

        return stringToken;
    }

    public Usuario obtenerUsuarioPorCorreo(String correo){
        return usuarioRepository.findByCorreo(correo).get();
    }

    //Lista de Usuarios
    public List<Usuario> obtenerListaUsuariosPorRol(String rolString){
        RolEnum rolEnum = RolEnum.valueOf(rolString.toUpperCase());
        Rol rol = rolService.obtenerRol(rolEnum);
        return usuarioRepository.findAllByRol(rol);
    }




}
