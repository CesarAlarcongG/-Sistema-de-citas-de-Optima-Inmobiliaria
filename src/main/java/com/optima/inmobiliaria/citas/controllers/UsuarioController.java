package com.optima.inmobiliaria.citas.controllers;

import com.optima.inmobiliaria.citas.dto.LoginDto;
import com.optima.inmobiliaria.citas.dto.RegistroDto;
import com.optima.inmobiliaria.citas.persistence.models.Usuario;
import com.optima.inmobiliaria.citas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    //Este endpoint es para registrar a nuevos usuarios
    @PostMapping("/registro")
    public ResponseEntity<?> registraUsuario(@RequestBody RegistroDto registroDto){
        String correo = registroDto.getCorreo();
        //1. Validamos el formato del correo
        if (!usuarioService.validarFormatoCorreo(correo)){
            return new ResponseEntity<>("El formato del correo es inválido", HttpStatus.BAD_REQUEST);
        }

        //2. Verificar existencia de usuario
        if (usuarioService.validarExistenciaUsuario(correo)){
            return new ResponseEntity<>("El correo ya fue registrado en otra cuenta", HttpStatus.CONFLICT);
        }

        //3. Si las credenciales pasaron ambos filtros, se registra la cuenta
        String token = usuarioService.registrarCuenta(registroDto);

        //4. Retorno el token
        return new ResponseEntity<>("token:"+token, HttpStatus.CREATED);

    }


    @PostMapping("/login")
    public ResponseEntity<?> loggin(@RequestBody LoginDto loginDto) {
        try {
           //Generamos un token cuando validamos si el usuario existe
            String response = usuarioService.loggin(loginDto);

            // Verificamos si el string es nulo para indicar que hay un error
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Usuario no encontrado"));
            }

            // Devolver la respuesta en caso de éxito
            return ResponseEntity.ok("token: "+response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenciales inválidas"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error interno del servidor"));
        }
    }

    @GetMapping("/agentes/{rol}")
    public List<Usuario> listaDeUsuarios(@PathVariable String rol){
        String rolUpper = rol.toUpperCase();
        return usuarioService.obtenerListaUsuariosPorRol(rolUpper);
    }

}
