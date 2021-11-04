package cl.ucn.dge.salud.visado_certificados.model;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true,nullable = false)
    private Integer id;

    @Column(name="nombreRol",unique = true,nullable = false)
    private String nombreRol;

    public Rol() {
    }

    public Rol(Integer id, String nombreRol) {
        this.id = id;
        this.nombreRol = nombreRol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
