package cl.ucn.dge.salud.visadocertificados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="documentos")
public class Documento {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @JsonIgnore
    @Lob
    private byte[] data;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo", nullable = true)
    private posiblesTiposDocumentos tipo;

    @JsonProperty("tipo_archivo")
    @Column(name="tipo_archivo", nullable = true)
    private String tipoArchivo;

    @Column(name="nombre")
    private String nombre;

    public Documento() {
    }

    public Documento(byte[] data, posiblesTiposDocumentos tipo, String tipoArchivo, String nombre) {
        this.data = data;
        this.tipo = tipo;
        this.tipoArchivo = tipoArchivo;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public posiblesTiposDocumentos getTipo() {
        return tipo;
    }

    public void setTipo(posiblesTiposDocumentos tipo) {
        this.tipo = tipo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public enum posiblesTiposDocumentos {

        CERTIFICADO,
        RESPALDO
    }

    
}
