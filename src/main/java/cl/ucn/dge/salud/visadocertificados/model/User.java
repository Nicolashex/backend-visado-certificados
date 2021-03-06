package cl.ucn.dge.salud.visadocertificados.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false,unique = true)
    private Long id;

    @Column(name="correo",nullable = false,unique = true)
    private String correo;

    @Column(name="rut",nullable = false,unique = true)
    private String rut;

    @JsonIgnore
    @Column(name="contrasena",nullable = false)
    private String contrasena;

    @Column(name="nombre",nullable = false)
    private String nombre;

    @Column(name="primerApellido",nullable = false)
    private String primerApellido;

    @Column(name="segundoApellido",nullable = false)
    private String segundoApellido;

    @Column(name="telefono",nullable = false,length = 25)
    private String telefono;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carrera")
    private Carrera carrera;

    @Column(name="cargo")
    private String cargo;

    @Column(name="profesion")
    private String profesion;

    @ManyToOne()
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "id",
            referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private Rol rol;

    public User() {
    }

    public User(String correo, String rut, String contrasena, String nombre, String primerApellido, String segundoApellido, String telefono, Carrera carrera, Rol rol) {

        this.correo = correo;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.carrera = carrera;
        this.rol = rol;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
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

    public Rol getRoles() {
        return rol;
    }

    public void setRoles(Rol roles) {
        this.rol = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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


}
