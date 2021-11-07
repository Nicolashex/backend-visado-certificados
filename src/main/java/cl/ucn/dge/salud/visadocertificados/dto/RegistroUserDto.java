package cl.ucn.dge.salud.visadocertificados.dto;

import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.model.Rol;

import java.util.Collection;

public class RegistroUserDto {

    private String correo;
    private String rut;
    private String contrasena;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private int carrera;
    private String cargo;
    private String profesion;

    public RegistroUserDto(String correo, String rut, String contrasena, String nombre, String primerApellido, String segundoApellido, String telefono, int carrera, String cargo, String profesion) {
        this.correo = correo;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.carrera = carrera;
        this.cargo = cargo;
        this.profesion = profesion;
    }

    public RegistroUserDto() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCarrera() {
        return carrera;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public String toString() {
        return "RegistroUserDto{" +
                "correo='" + correo + '\'' +
                ", rut='" + rut + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", carrera=" + carrera +
                ", cargo='" + cargo + '\'' +
                ", profesion='" + profesion + '\'' +
                '}';
    }
}
