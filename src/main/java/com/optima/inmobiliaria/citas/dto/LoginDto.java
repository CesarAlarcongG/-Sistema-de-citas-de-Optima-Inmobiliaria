package com.optima.inmobiliaria.citas.dto;

public class LoginDto {

    private String correo;
    private String contraseña;

    //constructores
    public LoginDto(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public LoginDto() {
    }

    //Getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
