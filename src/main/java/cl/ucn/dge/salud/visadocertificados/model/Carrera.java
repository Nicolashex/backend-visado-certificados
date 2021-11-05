package cl.ucn.dge.salud.visadocertificados.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="carreras")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
    private Long id;

    @Column(name="nombre",unique = true, nullable = false)
    private String nombre;

    @Column(name="sede", nullable = false)
    private String sede;

    @Column(name="departamento", nullable = true)
    private String departamento;

    @OneToMany(mappedBy ="carrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> estudiantes;

    public Carrera() {
    }

    public Carrera(String nombre, String sede, String departamento, List<User> estudiantes) {
        this.nombre = nombre;
        this.sede = sede;
        this.departamento = departamento;
        this.estudiantes = estudiantes;
    }

    public List<User> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<User> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
