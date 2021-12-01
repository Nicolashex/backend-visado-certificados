package cl.ucn.dge.salud.visadocertificados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name="tipo_archivo", nullable = true)
    private String tipoArchivo;

    public Documento() {
    }

    public Documento(byte[] data, posiblesTiposDocumentos tipo, String tipoArchivo) {
        this.data = data;
        this.tipo = tipo;
        this.tipoArchivo = tipoArchivo;
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


    public enum posiblesTiposDocumentos {

        CERTIFICADO,
        RESPALDO
    }
}
