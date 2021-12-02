package cl.ucn.dge.salud.visadocertificados.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegistroAdminDto {

    @NotBlank(message = "Correo no puede estar en blanco")
    @Email(message = "El correo debe ser valido")
    private String correo;

    //@ValidacionRut(message = "Se requiere un rut valido")
    @NotBlank(message = "Rut no puede estar en blanco")
    private String rut;

    @NotBlank(message = "Credencial no puede estar en blanco")
    private String contrasena;

    @NotBlank(message = "Se requiere un nombre")
    @Pattern(message = "Nombre no valido",
            regexp = "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$")
    private String nombre;

    @NotBlank(message = "Se requiere un apellido")
    @Pattern(message = "Apellido no valido",
            regexp = "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$")
    private String primerApellido;

    @NotBlank(message = "Se requiere un segundo apellido")
    @Pattern(message = "Apellido no valido",
            regexp = "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$")
    private String segundoApellido;

    private String telefono;

    private String cargo;

    private String profesion;

    public RegistroAdminDto() {
    }

    public RegistroAdminDto(String correo, String rut, String contrasena, String nombre, String primerApellido, String segundoApellido, String telefono, String cargo, String profesion) {
        this.correo = correo;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.cargo = cargo;
        this.profesion = profesion;
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
}
