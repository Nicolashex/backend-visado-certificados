package cl.ucn.dge.salud.visado_certificados.model;

import javax.persistence.*;

@Entity
@Table(name="documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
    private Integer id;

    @Column(name="ruta",unique = true, nullable = false)
    private String ruta;

    @Column(name="tipo", nullable = true)
    private String tipo;

    public Documento() {
    }

    public Documento(Integer id, String ruta, String tipo) {
        this.id = id;
        this.ruta = ruta;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
